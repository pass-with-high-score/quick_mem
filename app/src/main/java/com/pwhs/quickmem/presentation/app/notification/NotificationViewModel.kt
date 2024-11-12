package com.pwhs.quickmem.presentation.app.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private var userId: String = ""

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<NotificationUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun setArgs(args: NotificationArgs) {
        if (userId != args.userId) {
            userId = args.userId
            if (userId.isNotEmpty()) {
                loadNotifications()
            } else {
                Timber.e("User ID is missing.")
                sendErrorEvent("User ID is missing.")
            }
        }
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""

            if (userId.isEmpty()) {
                Timber.e("User ID is missing. Unable to load notifications.")
                sendErrorEvent("User ID is missing.")
                return@launch
            }

            Timber.d("Loading notifications for userId: $userId with token: $token")

            repository.loadNotifications(userId, token).collect { result ->
                when (result) {
                    is Resources.Loading -> {
                        Timber.d("Loading notifications...")
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resources.Success -> {
                        Timber.d("Notifications loaded successfully: ${result.data?.size}")
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                notifications = result.data ?: emptyList()
                            )
                        }
                    }
                    is Resources.Error -> {
                        Timber.e("Error loading notifications: ${result.message}")
                        _uiState.update { state ->
                            state.copy(isLoading = false)
                        }
                        sendErrorEvent(result.message ?: "Failed to load notifications")
                    }
                }
            }
        }
    }

    fun markNotificationAsRead(notificationId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            Timber.d("Marking notification as read for ID: $notificationId with token: $token")

            repository.markNotificationAsRead(notificationId, token).collect { result ->
                when (result) {
                    is Resources.Success -> {
                        Timber.d("Notification marked as read successfully for ID: $notificationId")
                        _uiState.update { state ->
                            state.copy(
                                notifications = state.notifications.map { notification ->
                                    if (notification.id == notificationId) {
                                        notification.copy(isRead = true)
                                    } else notification
                                }
                            )
                        }
                    }
                    is Resources.Error -> {
                        Timber.e("Error marking notification as read for ID: $notificationId - ${result.message}")
                        sendErrorEvent(result.message ?: "Failed to mark notification as read")
                    }
                    else -> {
                        Timber.d("Marking notification as read in progress for ID: $notificationId")
                    }
                }
            }
        }
    }

    fun deleteNotification(notificationId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            Timber.d("Deleting notification for ID: $notificationId with token: $token")

            repository.deleteNotification(notificationId, token).collect { result ->
                when (result) {
                    is Resources.Success -> {
                        Timber.d("Notification deleted successfully for ID: $notificationId")
                        _uiState.update { state ->
                            state.copy(
                                notifications = state.notifications.filterNot { it.id == notificationId }
                            )
                        }
                    }
                    is Resources.Error -> {
                        Timber.e("Error deleting notification for ID: $notificationId - ${result.message}")
                        sendErrorEvent(result.message ?: "Failed to delete notification")
                    }
                    else -> {
                        Timber.d("Deleting notification in progress for ID: $notificationId")
                    }
                }
            }
        }
    }

    private fun sendErrorEvent(message: String) {
        viewModelScope.launch {
            Timber.e("Sending error event: $message")
            _uiEvent.send(NotificationUiEvent.ShowError(message))
        }
    }
}
