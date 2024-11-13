package com.pwhs.quickmem.presentation.app.settings.user_info.username


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.UpdateUsernameRequestModel
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
class UpdateUsernameSettingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UpdateUsernameSettingUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UpdateUsernameSettingUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val userId = savedStateHandle.get<String>("userId") ?: ""
        val username = savedStateHandle.get<String>("username") ?: ""
        _uiState.update {
            it.copy(
                id = userId,
                username = username
            )
        }
    }

    fun onEvent(event: UpdateUsernameSettingUiAction) {
        when (event) {
            is UpdateUsernameSettingUiAction.OnUsernameChanged -> {
                _uiState.update {
                    it.copy(
                        username = event.username,
                        errorMessage = ""
                    )
                }
            }

            is UpdateUsernameSettingUiAction.OnSaveClicked -> {
                saveUsername()
            }
        }
    }

    private fun saveUsername() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = _uiState.value.id
            val username = _uiState.value.username
            authRepository.updateUsername(
                token,
                UpdateUsernameRequestModel(userId = userId, username = username)
            )
                .collect { resource ->
                    when (resource) {
                        is Resources.Error -> {
                            _uiState.update {
                                it.copy(
                                    errorMessage = resource.message ?: "An error occurred",
                                    isLoading = false
                                )
                            }
                            _uiEvent.send(
                                UpdateUsernameSettingUiEvent.OnError(
                                    resource.message ?: "An error occurred"
                                )
                            )
                        }

                        is Resources.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }

                        is Resources.Success -> {
                            appManager.saveUsername(resource.data?.username ?: username)
                            _uiEvent.send(UpdateUsernameSettingUiEvent.OnUsernameChanged)
                        }
                    }
                }
        }
    }
}