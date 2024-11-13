package com.pwhs.quickmem.presentation.app.settings.user_info.username

sealed class UpdateUsernameSettingUiEvent {
    data object OnUsernameChanged : UpdateUsernameSettingUiEvent()
    data class OnError(val errorMessage: String) : UpdateUsernameSettingUiEvent()
}