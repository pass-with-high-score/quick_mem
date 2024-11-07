package com.pwhs.quickmem.presentation.app.user_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserDetailUiState())
    val uiState: StateFlow<UserDetailUiState> = _uiState

    private val _uiEvent = Channel<UserDetailUiEvent>(Channel.BUFFERED)
    val uiEvent: Flow<UserDetailUiEvent> = _uiEvent.receiveAsFlow()

    init {
        val userId = savedStateHandle.get<String>("userId") ?: ""
        loadUserDetails(userId)
    }

    private fun loadUserDetails(userId: String) {
        viewModelScope.launch {
            tokenManager.accessToken
                .onStart {
                    _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                }
                .collect { token ->
                    if (!token.isNullOrEmpty()) {
                        userRepository.getUserById(userId, token).collect { resource ->
                            when (resource) {
                                is Resources.Loading -> {
                                    _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                                }
                                is Resources.Success -> {
                                    val user = resource.data
                                    _uiState.value = _uiState.value.copy(
                                        isLoading = false,
                                        userName = user?.username.orEmpty(),
                                        avatarUrl = user?.avatarUrl.orEmpty(),
                                        errorMessage = null
                                    )
                                }
                                is Resources.Error -> {
                                    _uiState.value = _uiState.value.copy(isLoading = false)
                                    sendUiEvent(UserDetailUiEvent.ShowError(resource.message ?: "An unknown error occurred"))
                                }
                            }
                        }
                    } else {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        sendUiEvent(UserDetailUiEvent.ShowError("Token is missing or invalid"))
                    }
                }
        }
    }


    private fun sendUiEvent(event: UserDetailUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun refresh() {
        sendUiEvent(UserDetailUiEvent.Refresh)
        loadUserDetails(uiState.value.userId)
    }
}
