package com.pwhs.quickmem.presentation.app.settings.user_info.full_name

sealed class UpdateFullNameSettingUiEvent {
    data object OnFullNameChanged : UpdateFullNameSettingUiEvent()
    data class OnError(val errorMessage: String) : UpdateFullNameSettingUiEvent()
}