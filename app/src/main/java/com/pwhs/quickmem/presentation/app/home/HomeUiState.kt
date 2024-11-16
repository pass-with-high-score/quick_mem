package com.pwhs.quickmem.presentation.app.home

import com.pwhs.quickmem.domain.model.notification.GetNotificationResponseModel
import com.revenuecat.purchases.CustomerInfo

data class HomeUiState(
    val isLoading: Boolean = false,
    val userId: String = "",
    val streakCount: Int = 0,
    val customerInfo: CustomerInfo? = null,
    val notifications: List<GetNotificationResponseModel> = emptyList(),
    val error: String? = null
)