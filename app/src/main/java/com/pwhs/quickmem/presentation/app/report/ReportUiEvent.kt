package com.pwhs.quickmem.presentation.app.report

sealed class ReportUiEvent {
    data object OnSubmitReport : ReportUiEvent()
    data class OnError(val message: String) : ReportUiEvent()
}