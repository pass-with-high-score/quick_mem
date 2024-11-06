package com.pwhs.quickmem.presentation.app.settings.user_info.changepassword

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.ChangePasswordRequestModel
import com.pwhs.quickmem.domain.repository.AuthRepository
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
        val userId = savedStateHandle.get<String>("userId") ?: ""
        _uiState.update {
            it.copy(id = userId)
        }
    }

    fun onEvent(event: ChangePasswordSettingUiAction) {
        when (event) {
            is ChangePasswordSettingUiAction.OnCurrentPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        currentPassword = event.currentPassword,
                        errorMessage = ""
                    )
                }
            }
            is ChangePasswordSettingUiAction.OnNewPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        newPassword = event.newPassword,
                        errorMessage = ""
                    )
                }
            }
            is ChangePasswordSettingUiAction.OnConfirmPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        confirmPassword = event.confirmPassword,
                        errorMessage = ""
                    )
                }
            }
            is ChangePasswordSettingUiAction.OnSaveClicked -> {
                changePassword()
            }
        }
    }

    private fun changePassword() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = _uiState.value.id
            val currentPassword = _uiState.value.currentPassword
            val newPassword = _uiState.value.newPassword
            val confirmPassword = _uiState.value.confirmPassword

            if (newPassword != confirmPassword) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Passwords do not match",
                        isLoading = false
                    )
                }
                _uiEvent.send(ChangePasswordSettingUiEvent.OnError("Passwords do not match"))
            }

            authRepository.changePassword(
                token,
                ChangePasswordRequestModel(userId = userId, currentPassword = currentPassword, newPassword = newPassword, confirmPassword = confirmPassword)
            ).collect { resource ->
                when (resource) {
                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = resource.message ?: "An error occurred",
                                isLoading = false
                            )
                        }
                        _uiEvent.send(ChangePasswordSettingUiEvent.OnError(resource.message ?: "An error occurred"))
                    }

                    is Resources.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resources.Success -> {
                        _uiEvent.send(ChangePasswordSettingUiEvent.OnPasswordChanged)
                    }
                }
            }
        }
    }
}
