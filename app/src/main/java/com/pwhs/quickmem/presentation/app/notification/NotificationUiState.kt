package com.pwhs.quickmem.presentation.app.notification

import com.pwhs.quickmem.domain.model.notification.GetNotificationResponseModel

data class NotificationUiState(
    val userId: String = "",
    val isOwner: Boolean = false,
    val notifications: List<GetNotificationResponseModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
