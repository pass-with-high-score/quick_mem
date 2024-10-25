package com.pwhs.quickmem.presentation.app.settings

sealed class SettingsUiEvent {
    object ConfirmLogout : SettingsUiEvent()
    object ShowFeedbackDialog : SettingsUiEvent()
    object ShowReportDialog : SettingsUiEvent()
    object ConfirmDeleteAccount : SettingsUiEvent()
}
