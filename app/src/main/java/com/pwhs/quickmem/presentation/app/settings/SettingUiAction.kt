package com.pwhs.quickmem.presentation.app.settings

sealed class SettingUiAction {
    data object Logout : SettingUiAction()
    data class OnChangePassword(val password: String) : SettingUiAction()
    data object OnSubmitClick : SettingUiAction()
    data class OnChangeCanChangeInfo(val canChangeInfo: Boolean) : SettingUiAction()
    data class OnChangeType(val changeType: SettingChangeValueEnum) : SettingUiAction()
    data class OnChangePushNotifications(val isPushNotificationsEnabled: Boolean) :
        SettingUiAction()

    data class OnChangeAppPushNotifications(val isAppPushNotificationsEnabled: Boolean) :
        SettingUiAction()
}