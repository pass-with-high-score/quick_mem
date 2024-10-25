package com.pwhs.quickmem.presentation.app.settings

sealed class SettingsUiAction {
    data object Logout : SettingsUiAction()
    data object DeleteAccount : SettingsUiAction()
    data object GiveFeedback : SettingsUiAction()
    data object ReportProblem : SettingsUiAction()
}
