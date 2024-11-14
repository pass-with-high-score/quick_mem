package com.pwhs.quickmem.presentation.app.profile.choose_picture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
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
                val avatarId = extractAvatarIdFromUrl(event.avatarUrl)
                if (avatarId != null) {
                    _uiState.update { it.copy(selectedAvatarUrl = avatarId) }
                } else {
                    Timber.e("Invalid avatar URL: ${event.avatarUrl}")
                }
            }

            ChoosePictureUiAction.SaveClicked -> {
                val selectedAvatarId = _uiState.value.selectedAvatarUrl
                if (selectedAvatarId != null) {
                    updateAvatar(selectedAvatarId)
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
        return matchResult?.groups?.get(1)?.value
    }

    private fun getListAvatar() {
        viewModelScope.launch {
            authRepository.getAvatar().collectLatest { resource ->
                when (resource) {
                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        Timber.e("Error fetching avatars: ${resource.message}")
                    }

                    is Resources.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resources.Success -> _uiState.update {
                        it.copy(isLoading = false, avatarUrls = resource.data ?: emptyList())
                    }
                }
            }
        }
    }

    private fun updateAvatar(avatarId: String) {
        viewModelScope.launch {
            try {
                val token = tokenManager.accessToken.firstOrNull() ?: ""
                val userId = appManager.userId.firstOrNull() ?: ""

                if (token.isEmpty() || userId.isEmpty()) {
                    Timber.e("Token or UserId is missing")
                    _uiState.update { it.copy(isLoading = false) }
                    return@launch
                }

                Timber.d("Starting updateAvatar API call")
                Timber.d("Token: $token, UserID: $userId")
                Timber.d("avatarId: $avatarId")
                val jsonBody = """
            {
                "avatar": "$avatarId"
            }
        """.trimIndent()

                val requestBody = jsonBody.toRequestBody("application/json".toMediaTypeOrNull())

                authRepository.updateAvatar(token, userId, requestBody).collect { resource ->
                    when (resource) {
                        is Resources.Error -> {
                            _uiState.update {
                                Timber.e("Update Avatar Failed: ${resource.message}")
                                it.copy(isLoading = false)
                            }
                        }

                        is Resources.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is Resources.Success -> {
                            Timber.d("Update Avatar Success: ${resource.data?.avatarUrl}")
                            _uiEvent.send(
                                ChoosePictureUiEvent.AvatarUpdated(
                                    resource.data?.avatarUrl ?: ""
                                )
                            )
                            _uiState.update { it.copy(isLoading = false) }
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e("Exception during updateAvatar: ${e.message}, cause: ${e.cause}")
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
