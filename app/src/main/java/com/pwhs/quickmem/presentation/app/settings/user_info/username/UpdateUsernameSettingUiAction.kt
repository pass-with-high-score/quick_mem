package com.pwhs.quickmem.presentation.app.settings.user_info.username

sealed class UpdateUsernameSettingUiAction {
    data class OnUsernameChanged(val username: String) : UpdateUsernameSettingUiAction()
    data object OnSaveClicked : UpdateUsernameSettingUiAction()
}