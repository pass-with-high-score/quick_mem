package com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses

import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.data.OpenSourceLicensesData
import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.data.SourceLicensesList

data class OpenSourceUiState(
    val isLoading: Boolean = false,
    val licenses: List<OpenSourceLicensesData> = SourceLicensesList,
    val errorMessage: String? = null
)