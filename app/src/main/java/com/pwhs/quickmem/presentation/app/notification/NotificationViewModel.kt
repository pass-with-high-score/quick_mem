package com.pwhs.quickmem.presentation.app.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository,
    private val tokenManager: TokenManager
) : ViewModel() {


    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    fun handleAction(action: NotificationUiAction) {
        when (action) {
            is NotificationUiAction.SetArgs -> loadNotifications(action.userId)
            is NotificationUiAction.LoadNotifications -> loadNotifications(action.userId)
            is NotificationUiAction.MarkAsRead -> markNotificationAsRead(action.notificationId)
            is NotificationUiAction.DeleteNotification -> deleteNotification(action.notificationId)
            NotificationUiAction.RefreshNotifications -> {}
        }
    }

    private fun loadNotifications(userId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: run {
                sendErrorEvent("Access token is missing.")
                return@launch
            }

            repository.loadNotifications(userId, token).collect { result ->
                when (result) {
                    is Resources.Loading -> _uiState.update { it.copy(isLoading = true, error = null) }
                    is Resources.Success -> _uiState.update { it.copy(isLoading = false, notifications = result.data ?: emptyList(), error = null) }
                    is Resources.Error -> sendErrorEvent(result.message ?: "Failed to load notifications")
                }
            }
        }
    }

    private fun markNotificationAsRead(notificationId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: run {
                sendErrorEvent("Access token is missing.")
                return@launch
            }

            repository.markNotificationAsRead(notificationId, token).collect { result ->
                when (result) {
                    is Resources.Success -> _uiState.update { state ->
                        state.copy(
                            notifications = state.notifications.map { notification ->
                                if (notification.id == notificationId) notification.copy(isRead = true) else notification
                            }
                        )
                    }
                    is Resources.Error -> sendErrorEvent(result.message ?: "Failed to mark notification as read")
                    else -> {}
                }
            }
        }
    }

    private fun deleteNotification(notificationId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: run {
                sendErrorEvent("Access token is missing.")
                return@launch
            }

            repository.deleteNotification(notificationId, token).collect { result ->
                when (result) {
                    is Resources.Success -> _uiState.update { state ->
                        state.copy(
                            notifications = state.notifications.filterNot { it.id == notificationId }
                        )
                    }
                    is Resources.Error -> sendErrorEvent(result.message ?: "Failed to delete notification")
                    else -> {}
                }
            }
        }
    }

    private fun sendErrorEvent(message: String) {
        _uiState.update { state ->
            state.copy(error = message)
        }
    }
}
