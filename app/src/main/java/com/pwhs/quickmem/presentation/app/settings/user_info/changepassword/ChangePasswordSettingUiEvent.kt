package com.pwhs.quickmem.presentation.app.settings.user_info.changepassword

sealed class ChangePasswordSettingUiEvent {
    data class OnError(val errorMessage: String) : ChangePasswordSettingUiEvent()
    object OnPasswordChanged : ChangePasswordSettingUiEvent()
}