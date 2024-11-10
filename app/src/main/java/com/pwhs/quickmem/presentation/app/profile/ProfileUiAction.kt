package com.pwhs.quickmem.presentation.app.profile

import com.revenuecat.purchases.CustomerInfo

sealed class ProfileUiAction {
    data object LoadProfile : ProfileUiAction()
    data class OnChangeCustomerInfo(val customerInfo: CustomerInfo) : ProfileUiAction()
}