package com.pwhs.quickmem.presentation.app.profile

sealed class ProfileUiAction {
    data object LoadProfile : ProfileUiAction()
    data object Logout : ProfileUiAction()
}