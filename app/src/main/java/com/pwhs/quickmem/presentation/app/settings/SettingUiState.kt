package com.pwhs.quickmem.presentation.app.settings

import com.pwhs.quickmem.core.data.enums.LanguageCode
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
    val languageCode: LanguageCode = LanguageCode.EN,
    val changeType: SettingChangeValueEnum = SettingChangeValueEnum.NONE,
    val isPushNotificationsEnabled: Boolean = false,
    val isAppPushNotificationsEnabled: Boolean = false,
    val customerInfo: CustomerInfo? = null,
)
