package com.pwhs.quickmem.presentation.app.home

import com.revenuecat.purchases.CustomerInfo

sealed class HomeUIAction {
    data class OnChangeAppPushNotifications(val isAppPushNotificationsEnabled: Boolean) :
        HomeUIAction()
    data class OnChangeCustomerInfo(val customerInfo: CustomerInfo) : HomeUIAction()
}