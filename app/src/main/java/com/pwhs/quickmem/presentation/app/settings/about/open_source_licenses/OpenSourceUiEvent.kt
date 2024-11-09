package com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses

import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.data.OpenSourceLicensesData

sealed class OpenSourceUiEvent {
    data class NavigateToDetail(val license: OpenSourceLicensesData) : OpenSourceUiEvent()
    data class ShowError(val message: String) : OpenSourceUiEvent()
}