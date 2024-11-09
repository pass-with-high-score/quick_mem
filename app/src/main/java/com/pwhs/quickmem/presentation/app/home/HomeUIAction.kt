package com.pwhs.quickmem.presentation.app.home

sealed class HomeUIAction {
    data class OnChangeAppPushNotifications(val isAppPushNotificationsEnabled: Boolean) :
        HomeUIAction()
}