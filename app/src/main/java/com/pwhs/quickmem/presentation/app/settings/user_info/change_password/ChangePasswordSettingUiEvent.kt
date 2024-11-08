package com.pwhs.quickmem.presentation.app.settings.user_info.change_password

sealed class ChangePasswordSettingUiEvent {
    data class OnError(val errorMessage: String) : ChangePasswordSettingUiEvent()
    data object OnPasswordChanged : ChangePasswordSettingUiEvent()
}