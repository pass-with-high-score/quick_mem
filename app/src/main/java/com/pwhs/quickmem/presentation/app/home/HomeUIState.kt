package com.pwhs.quickmem.presentation.app.home

import com.revenuecat.purchases.CustomerInfo

data class HomeUIState(
    val isLoading: Boolean = false,
    val userId: String = "",
    val streakCount: Int = 0,
    val customerInfo: CustomerInfo? = null,
)