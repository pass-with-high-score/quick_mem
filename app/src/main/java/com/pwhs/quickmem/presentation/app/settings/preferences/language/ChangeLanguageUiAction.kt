package com.pwhs.quickmem.presentation.app.settings.preferences.language

import com.pwhs.quickmem.core.data.enums.LanguageCode

sealed class ChangeLanguageUiAction {
    data class ChangeLanguage(val languageCode: LanguageCode) : ChangeLanguageUiAction()
}