package com.pwhs.quickmem.presentation.app.profile

import com.revenuecat.purchases.CustomerInfo

data class ProfileUiState(
    val userAvatar: String = "",
    val username: String = "",
    val customerInfo: CustomerInfo? = null,
    val isLoading: Boolean = false
)