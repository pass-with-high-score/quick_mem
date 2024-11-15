package com.pwhs.quickmem.presentation.app.notification

sealed class NotificationUiAction {
    data class SetArgs(val userId: String) : NotificationUiAction()
    data class LoadNotifications(val userId: String) : NotificationUiAction()
    data class MarkAsRead(val notificationId: String) : NotificationUiAction()
    data class DeleteNotification(val notificationId: String) : NotificationUiAction()
    data object RefreshNotifications : NotificationUiAction()
}
