package com.pwhs.quickmem.presentation.app.report

sealed class ReportUiAction {
    data class OnReasonChanged(val reason: String) : ReportUiAction()
    data object OnSubmitReport : ReportUiAction()
}