package com.pwhs.quickmem.presentation.app.notification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<NotificationUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val userId = savedStateHandle.get<String>("userId") ?: ""
        if (userId.isEmpty()) {
            viewModelScope.launch {
                _uiEvent.send(NotificationUiEvent.ShowError("User ID is missing"))
            }
        }

        _uiState.update {
            it.copy(userId = userId)
        }
        loadNotifications()
    }

    fun onEvent(event: NotificationUiAction) {
        when (event) {
            NotificationUiAction.LoadNotifications -> loadNotifications()
            is NotificationUiAction.MarkAsRead -> markAsRead(event.notificationId)
            is NotificationUiAction.DeleteNotification -> deleteNotification(event.notificationId)
        }
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = uiState.value.userId

            try {
                val notifications = apiService.getNotificationsByUserId(token, userId)
                _uiState.value = _uiState.value.copy(notifications = notifications)
            } catch (e: Exception) {
                Timber.e("Error loading notifications: ${e.localizedMessage}")
                _uiEvent.send(NotificationUiEvent.ShowError("Failed to load notifications"))
            }
        }
    }

    private fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            try {
                apiService.markNotificationAsRead(token, notificationId)
                _uiState.value = _uiState.value.copy(
                    notifications = _uiState.value.notifications.map {
                        if (it.id == notificationId) it.copy(isRead = true) else it
                    }
                )
            } catch (e: Exception) {
                Timber.e("Error marking notification as read: ${e.localizedMessage}")
                _uiEvent.send(NotificationUiEvent.ShowError("Failed to mark notification as read"))
            }
        }
    }


    private fun deleteNotification(notificationId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""

            try {
                apiService.deleteNotification(token, notificationId)
                _uiState.value = _uiState.value.copy(
                    notifications = _uiState.value.notifications.filterNot { it.id == notificationId }
                )
            } catch (e: Exception) {
                Timber.e("Error deleting notification: ${e.localizedMessage}")
                _uiEvent.send(NotificationUiEvent.ShowError("Failed to delete notification"))
            }
        }
    }
}

