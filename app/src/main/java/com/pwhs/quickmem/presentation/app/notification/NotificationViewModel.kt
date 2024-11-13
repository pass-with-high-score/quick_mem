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
import java.util.UUID
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

    fun handleAction(action: NotificationUiAction) {
        when (action) {
            is NotificationUiAction.LoadNotifications -> loadNotifications()
            is NotificationUiAction.MarkAsRead -> markNotificationAsRead(action.notificationId)
            is NotificationUiAction.DeleteNotification -> deleteNotification(action.notificationId)
            is NotificationUiAction.RefreshNotifications -> refreshNotifications()
        }
    }

    fun setArgs(args: NotificationArgs) {
        if (userId != args.userId) {
            userId = args.userId
            if (userId.isNotEmpty()) {
                handleAction(NotificationUiAction.LoadNotifications)
            } else {
                sendErrorEvent("User ID is missing.")
            }
        }
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""

            if (userId.isEmpty()) {
                _uiState.update { it.copy(error = "User ID is missing.") }
                return@launch
            }

            repository.loadNotifications(userId, token).collect { result ->
                when (result) {
                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }
                    is Resources.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                notifications = result.data ?: emptyList(),
                                error = null
                            )
                        }
                    }
                    is Resources.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = result.message ?: "Failed to load notifications"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun markNotificationAsRead(notificationId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""

            if (!isValidUUID(notificationId)) {
                sendErrorEvent("Invalid notification ID format")
                return@launch
            }

            repository.markNotificationAsRead(notificationId, token).collect { result ->
                when (result) {
                    is Resources.Loading -> {}
                    is Resources.Success -> {
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
                        sendErrorEvent(result.message ?: "Failed to mark notification as read")
                    }
                }
            }
        }
    }

    private fun isValidUUID(uuid: String): Boolean {
        return try {
            UUID.fromString(uuid)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    private fun deleteNotification(notificationId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            if (token.isEmpty()) {
                return@launch
            }
            repository.deleteNotification(notificationId, token).collect { result ->
                when (result) {
                    is Resources.Loading -> {}
                    is Resources.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                notifications = state.notifications.filterNot { it.id == notificationId }
                            )
                        }
                    }
                    is Resources.Error -> {
                        sendErrorEvent(result.message ?: "Failed to delete notification")
                    }
                }
            }
        }
    }

    private fun refreshNotifications() {
        loadNotifications()
    }

    private fun sendErrorEvent(message: String) {
        viewModelScope.launch {
            _uiEvent.send(NotificationUiEvent.ShowError(message))
        }
    }
}
