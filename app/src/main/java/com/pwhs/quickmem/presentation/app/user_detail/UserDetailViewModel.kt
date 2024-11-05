package com.pwhs.quickmem.presentation.app.user_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UserDetailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val userId = savedStateHandle.get<String>("userId") ?: ""
        loadUserDetails(userId)
    }

    private fun loadUserDetails(userId: String) {
        viewModelScope.launch {
            val token = "your_token_here"
            userRepository.getUserById(userId, token).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                    }
                    is Resources.Success -> {
                        val user = resource.data
                        if (user != null) {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                userId = user.id,
                                userName = user.username,
                                userEmail = "",
                                avatarUrl = user.avatarUrl,
                                errorMessage = null
                            )
                        } else {
                            sendUiEvent(UserDetailUiEvent.ShowError("User data is null"))
                        }
                    }
                    is Resources.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        sendUiEvent(UserDetailUiEvent.ShowError(resource.message ?: "An unknown error occurred"))
                    }
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
