package com.pwhs.quickmem.presentation.app.notification

sealed class NotificationUiAction {
    data object LoadNotifications : NotificationUiAction()
    data class MarkAsRead(val notificationId: String) : NotificationUiAction()
    data class DeleteNotification(val notificationId: String) : NotificationUiAction()
}
