package com.pwhs.quickmem.presentation.auth.forgot_password.set_new_password

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.ResetPasswordRequestModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetNewPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(SetNewPasswordUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<SetNewPasswordUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val email = savedStateHandle.get<String>("email") ?: ""
        val resetPasswordToken = savedStateHandle.get<String>("resetPasswordToken") ?: ""
        val otp = savedStateHandle.get<String>("otp") ?: ""
        _uiState.update {
            it.copy(
                email = email,
                resetPasswordToken = resetPasswordToken,
                otp = otp
            )
        }
    }

    fun onEvent(event: SetNewPasswordUiAction) {
        when (event) {
            is SetNewPasswordUiAction.PasswordChanged -> {
                _uiState.update {
                    it.copy(
                        password = event.password,
                        passwordError = if (event.password.isBlank()) "Please enter a new password" else ""
                    )
                }
            }

            is SetNewPasswordUiAction.ConfirmPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        confirmPassword = event.confirmPassword,
                        confirmPasswordError = if (event.confirmPassword != uiState.value.password) "Passwords do not match" else ""
                    )
                }
            }

            is SetNewPasswordUiAction.Submit -> {
                if (validateInput()) {
                    resetPassword()
                } else {
                    Toast.makeText(getApplication(), "Invalid input", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        val passwordValid = uiState.value.password.isNotBlank()
        val confirmPasswordValid = uiState.value.confirmPassword == uiState.value.password

        _uiState.update {
            it.copy(
                passwordError = if (!passwordValid) "Please enter a new password" else "",
                confirmPasswordError = if (!confirmPasswordValid) "Passwords do not match" else ""
            )
        }

        return passwordValid && confirmPasswordValid
    }

    private fun resetPassword() {
        viewModelScope.launch {
            val password = uiState.value.password

            authRepository.resetPassword(
                ResetPasswordRequestModel(
                    email = uiState.value.email,
                    newPassword = password,
                    resetPasswordToken = uiState.value.resetPasswordToken,
                    otp = uiState.value.otp
                )
            ).collect { resource ->
                when (resource) {
                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _uiEvent.send(SetNewPasswordUiEvent.ResetFailure)
                    }

                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        _uiEvent.trySend(SetNewPasswordUiEvent.ResetSuccess)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _uiEvent.close()
    }
}
