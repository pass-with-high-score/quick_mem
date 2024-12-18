package com.pwhs.quickmem.presentation.app.profile.change_avatar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.UpdateAvatarRequestModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeAvatarViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val appManager: AppManager,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChangeAvatarUiState())
    val uiState: StateFlow<ChangeAvatarUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ChangeAvatarUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getCurrentAvatar()
        getListAvatar()
    }

    private fun getCurrentAvatar() {
        viewModelScope.launch {
            val currentAvatarUrl = appManager.userAvatarUrl.firstOrNull()
            if (currentAvatarUrl != null) {
                _uiState.update {
                    it.copy(
                        selectedAvatarUrl = currentAvatarUrl
                    )
                }
            }
        }
    }

    fun onEvent(event: ChangeAvatarUiAction) {
        when (event) {
            is ChangeAvatarUiAction.ImageSelected -> {
                _uiState.update {
                    it.copy(
                        selectedAvatarUrl = event.avatarUrl
                    )
                }
            }

            ChangeAvatarUiAction.SaveClicked -> {
                val selectedAvatarUrl = _uiState.value.selectedAvatarUrl
                if (selectedAvatarUrl != null) {
                    updateAvatar(selectedAvatarUrl)
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    private fun getListAvatar() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            authRepository.getAvatar(token = token).collect { resource ->
                when (resource) {
                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                    }

                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        val avatarUrls = resource.data ?: emptyList()
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                avatarUrls = avatarUrls,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateAvatar(avatarId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            val userAvatarUrl = appManager.userAvatarUrl.firstOrNull() ?: ""

            if (token.isEmpty() || userId.isEmpty()) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            // check if link avatar is the same as the current avatar
            if (userAvatarUrl.contains(avatarId)) {
                _uiEvent.send(
                    ChangeAvatarUiEvent.AvatarUpdated(_uiState.value.selectedAvatarUrl ?: "")
                )
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                return@launch
            }

            authRepository.updateAvatar(
                token, userId, UpdateAvatarRequestModel(avatarId)
            ).collect { resource ->
                when (resource) {
                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resources.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resources.Success -> {
                        val newAvatarUrl = resource.data?.avatarUrl ?: ""
                        appManager.saveUserAvatar(newAvatarUrl)
                        _uiEvent.send(
                            ChangeAvatarUiEvent.AvatarUpdated(newAvatarUrl)
                        )
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}
