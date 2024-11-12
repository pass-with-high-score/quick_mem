package com.pwhs.quickmem.presentation.app.settings.user_info.user_name

sealed class UpdateUsernameSettingUiEvent {
    data object OnUsernameChanged : UpdateUsernameSettingUiEvent()
    data class OnError(val errorMessage: String) : UpdateUsernameSettingUiEvent()
}