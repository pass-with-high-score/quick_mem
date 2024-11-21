package com.pwhs.quickmem.presentation.auth.welcome

import com.pwhs.quickmem.core.data.enums.LanguageCode

sealed class WelcomeUiAction {
    data class ChangeLanguage(val languageCode: LanguageCode) : WelcomeUiAction()
}