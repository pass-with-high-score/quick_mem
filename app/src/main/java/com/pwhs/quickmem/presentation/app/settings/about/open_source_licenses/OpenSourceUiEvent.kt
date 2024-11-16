package com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses

import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.data.LicensesModel

sealed class OpenSourceUiEvent {
    data class NavigateToDetail(val license: LicensesModel) : OpenSourceUiEvent()
    data class ShowError(val message: String) : OpenSourceUiEvent()
}