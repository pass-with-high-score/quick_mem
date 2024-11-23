package com.pwhs.quickmem.presentation.app.profile

import com.revenuecat.purchases.CustomerInfo

sealed class ProfileUiAction {
    data object Refresh : ProfileUiAction()
    data class OnChangeCustomerInfo(val customerInfo: CustomerInfo) : ProfileUiAction()
}