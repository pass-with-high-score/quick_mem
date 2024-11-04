package com.pwhs.quickmem.presentation.app.settings.user_info.full_name

sealed class UpdateFullNameSettingUiAction {
    data class OnFullNameChanged(val fullName: String) : UpdateFullNameSettingUiAction()
    data object OnSaveClicked : UpdateFullNameSettingUiAction()
}