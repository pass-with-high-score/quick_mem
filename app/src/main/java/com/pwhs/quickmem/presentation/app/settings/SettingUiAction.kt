package com.pwhs.quickmem.presentation.app.settings

sealed class SettingUiAction {
    data object Logout : SettingUiAction()
}