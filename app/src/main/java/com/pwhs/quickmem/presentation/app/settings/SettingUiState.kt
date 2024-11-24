package com.pwhs.quickmem.presentation.app.settings

import com.revenuecat.purchases.CustomerInfo

data class SettingUiState(
    val canChangeInfo: Boolean = false,
    val password: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val showBottomSheet: Boolean = false,
    val userId: String = "",
    val fullName: String = "",
    val username: String = "",
    val email: String = "",
    val role: String = "",
    val changeType: SettingChangeValueEnum = SettingChangeValueEnum.NONE,
    val isPushNotificationsEnabled: Boolean = false,
    val isAppPushNotificationsEnabled: Boolean = false,
    val customerInfo: CustomerInfo? = null,
)
