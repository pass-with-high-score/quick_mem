package com.pwhs.quickmem.presentation.app.notification

sealed class NotificationUiEvent {
    data class ShowError(val message: String) : NotificationUiEvent()
}
