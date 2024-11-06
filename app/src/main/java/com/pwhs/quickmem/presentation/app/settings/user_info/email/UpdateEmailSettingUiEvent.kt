package com.pwhs.quickmem.presentation.app.settings.user_info.email

sealed class UpdateEmailSettingUiEvent {
    data object OnEmailChanged : UpdateEmailSettingUiEvent()
    data class OnError(val errorMessage: String) : UpdateEmailSettingUiEvent()
}