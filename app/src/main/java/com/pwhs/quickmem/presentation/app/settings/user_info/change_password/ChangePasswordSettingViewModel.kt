package com.pwhs.quickmem.presentation.app.settings.user_info.change_password

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.ChangePasswordRequestModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.util.strongPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordSettingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tokenManager: TokenManager,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChangePasswordSettingUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ChangePasswordSettingUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val email = savedStateHandle.get<String>("email") ?: ""
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun onEvent(event: ChangePasswordSettingUiAction) {
        when (event) {
            is ChangePasswordSettingUiAction.OnCurrentPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        currentPassword = event.currentPassword,
                        errorCurrentPassword = ""
                    )
                }
            }

            is ChangePasswordSettingUiAction.OnNewPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        newPassword = event.newPassword,
                        errorNewPassword = ""
                    )
                }
            }

            is ChangePasswordSettingUiAction.OnConfirmPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        confirmPassword = event.confirmPassword,
                        errorConfirmPassword = ""
                    )
                }
            }

            is ChangePasswordSettingUiAction.OnSaveClicked -> {
                if (validatePassword(
                        _uiState.value.newPassword,
                        _uiState.value.confirmPassword,
                        _uiState.value.currentPassword
                    )
                ) {
                    changePassword()
                } else {
                    _uiEvent.trySend(ChangePasswordSettingUiEvent.OnError("Invalid password"))
                }
            }
        }
    }

    private fun changePassword() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val email = _uiState.value.email
            val currentPassword = _uiState.value.currentPassword
            val newPassword = _uiState.value.newPassword

            authRepository.changePassword(
                token,
                ChangePasswordRequestModel(
                    email = email,
                    oldPassword = currentPassword,
                    newPassword = newPassword
                )
            ).collect { resource ->
                when (resource) {
                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(
                                errorCurrentPassword = resource.message ?: "An error occurred",
                                isLoading = false
                            )
                        }
                        _uiEvent.send(
                            ChangePasswordSettingUiEvent.OnError(
                                resource.message ?: "An error occurred"
                            )
                        )
                    }

                    is Resources.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                        _uiEvent.send(ChangePasswordSettingUiEvent.OnPasswordChanged)
                    }
                }
            }
        }
    }

    private fun validatePassword(
        newPassword: String,
        confirmPassword: String,
        currentPassword: String
    ): Boolean {
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            _uiState.update {
                it.copy(
                    errorCurrentPassword = "Please fill in all fields",
                    errorNewPassword = "Please fill in all fields",
                    errorConfirmPassword = "Please fill in all fields",
                    isLoading = false
                )
            }
            return false
        }

        if (!newPassword.strongPassword()) {
            _uiState.update {
                it.copy(
                    errorNewPassword = "Password must be at least 8 characters long",
                    isLoading = false
                )
            }
            return false
        }

        if (newPassword != confirmPassword) {
            _uiState.update {
                it.copy(
                    errorNewPassword = "Passwords do not match",
                    errorConfirmPassword = "Passwords do not match",
                    isLoading = false
                )
            }
            return false
        }

        if (currentPassword == newPassword) {
            _uiState.update {
                it.copy(
                    errorNewPassword = "New password must be different from the current password",
                    isLoading = false
                )
            }
            return false
        }
        return true
    }
}
