package com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses

sealed class OpenSourceUiAction {
    data class LicenseClicked(val licenseId: String) : OpenSourceUiAction()
    data object LoadLicenses : OpenSourceUiAction()
}