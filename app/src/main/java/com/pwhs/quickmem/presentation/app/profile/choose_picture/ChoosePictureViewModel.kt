package com.pwhs.quickmem.presentation.app.profile.choose_picture

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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChoosePictureViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val appManager: AppManager,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChoosePictureUiState())
    val uiState: StateFlow<ChoosePictureUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ChoosePictureUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getListAvatar()
    }

    fun onEvent(event: ChoosePictureUiAction) {
        when (event) {
            is ChoosePictureUiAction.ImageSelected -> {
                _uiState.update { it.copy(selectedAvatarUrl = event.avatarUrl) }
            }

            ChoosePictureUiAction.SaveClicked -> {
                val selectedAvatarUrl = _uiState.value.selectedAvatarUrl
                if (selectedAvatarUrl != null) {
                    val avatarId = extractAvatarIdFromUrl(selectedAvatarUrl)
                    if (avatarId != null) {
                        updateAvatar(avatarId)
                    } else {
                        Timber.e("Failed to extract avatar ID from URL: $selectedAvatarUrl")
                        _uiState.update { it.copy(isLoading = false) }
                    }
                } else {
                    Timber.e("No avatar selected")
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    private fun extractAvatarIdFromUrl(url: String): String? {
        val regex = """/avatar/(\d+)\.jpg""".toRegex()
        val matchResult = regex.find(url)
        val avatarId = matchResult?.groups?.get(1)?.value
        Timber.d("Extracted avatar ID is: $avatarId")
        return avatarId
    }

    private fun getListAvatar() {
        viewModelScope.launch {
            authRepository.getAvatar().collectLatest { resource ->
                when (resource) {
                    is Resources.Error -> {
                        Timber.e("Error fetching avatars: ${resource.message}")
                        _uiState.update { it.copy(isLoading = false) }
                    }

                    is Resources.Loading -> {
                        Timber.d("Loading avatars")
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        Timber.d("Successfully fetched avatars: ${resource.data}")
                        _uiState.update {
                            it.copy(isLoading = false, avatarUrls = resource.data ?: emptyList())
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

            if (token.isEmpty() || userId.isEmpty()) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            authRepository.updateAvatar(
                token, userId, UpdateAvatarRequestModel(avatarId)
            )
                .collect { resource ->
                    when (resource) {
                        is Resources.Error -> {
                            Timber.e("Update Avatar Failed: ${resource.message}")
                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resources.Loading -> {
                            Timber.d("Updating avatar - loading state")
                            _uiState.update {
                                it.copy(isLoading = true)
                            }
                        }

                        is Resources.Success -> {
                            val newAvatarUrl = resource.data?.avatarUrl ?: ""
                            Timber.d("Avatar updated successfully with new URL: $newAvatarUrl")
                            appManager.saveUserAvatar(newAvatarUrl)
                            _uiEvent.send(
                                ChoosePictureUiEvent.AvatarUpdated(newAvatarUrl)
                            )
                            _uiState.update { it.copy(isLoading = false) }
                        }
                    }
                }
        }
    }
}
