package com.pwhs.quickmem.presentation.app.settings

sealed class SettingUiEvent {
    data object NavigateToLogin : SettingUiEvent()
}