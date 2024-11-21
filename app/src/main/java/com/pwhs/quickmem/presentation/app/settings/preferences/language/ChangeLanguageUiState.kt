package com.pwhs.quickmem.presentation.app.settings.preferences.language

import com.pwhs.quickmem.core.data.enums.LanguageCode

data class ChangeLanguageUiState(
    val languageCode: LanguageCode = LanguageCode.EN
)
