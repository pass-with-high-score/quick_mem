package com.pwhs.quickmem.presentation.app.notification

import com.pwhs.quickmem.data.dto.notification.NotificationDto

data class NotificationUiState(
    val userId: String = "",
    val notifications: List<NotificationDto> = emptyList()
)
