package com.pwhs.quickmem.presentation.auth.forgot_password.verify_password

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordVerifyPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(ForgotPasswordVerifyPasswordUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ForgotPasswordVerifyPasswordUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ForgotPasswordVerifyPasswordUiAction) {
        when (event) {
            is ForgotPasswordVerifyPasswordUiAction.PasswordChanged -> {
                _uiState.update {
                    it.copy(
                        password = event.password,
                        passwordError = if (event.password.isBlank()) "Please enter a new password" else ""
                    )
                }
            }

            is ForgotPasswordVerifyPasswordUiAction.ConfirmPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        confirmPassword = event.confirmPassword,
                        confirmPasswordError = if (event.confirmPassword != uiState.value.password) "Passwords do not match" else ""
                    )
                }
            }

            is ForgotPasswordVerifyPasswordUiAction.Submit -> {
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
        //
        //
        _uiEvent.trySend(ForgotPasswordVerifyPasswordUiEvent.ResetSuccess)
    }

    override fun onCleared() {
        super.onCleared()
        _uiEvent.close()
    }
}
