package com.pwhs.quickmem.presentation.app.profile

import com.revenuecat.purchases.CustomerInfo

sealed class ProfileUiAction {
    data object Refresh : ProfileUiAction()
    data object LoadProfile : ProfileUiAction()
    data object OnNavigateToViewAllAchievements : ProfileUiAction()
    data class OnChangeCustomerInfo(val customerInfo: CustomerInfo) : ProfileUiAction()
}