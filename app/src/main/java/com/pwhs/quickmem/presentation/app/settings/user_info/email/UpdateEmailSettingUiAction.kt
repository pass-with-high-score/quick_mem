package com.pwhs.quickmem.presentation.app.settings.user_info.email

sealed class UpdateEmailSettingUiAction {
    data class OnEmailChanged(val email: String) : UpdateEmailSettingUiAction()
    object OnSaveClicked : UpdateEmailSettingUiAction()
}