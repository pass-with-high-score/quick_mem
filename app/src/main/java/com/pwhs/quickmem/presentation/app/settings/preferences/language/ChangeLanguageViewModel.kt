package com.pwhs.quickmem.presentation.app.settings.preferences.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.data.enums.LanguageCode
import com.pwhs.quickmem.core.datastore.AppManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeLanguageViewModel @Inject constructor(
    private val appManager: AppManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChangeLanguageUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val languageCode =
                appManager.languageCode.firstOrNull() ?: LanguageCode.EN.name.lowercase()
            _uiState.value = _uiState.value.copy(
                languageCode = when (languageCode) {
                    LanguageCode.EN.name.lowercase() -> LanguageCode.EN
                    LanguageCode.VI.name.lowercase() -> LanguageCode.VI
                    else -> LanguageCode.EN
                }
            )
        }
    }

    fun onEvent(event: ChangeLanguageUiAction) {
        when (event) {
            is ChangeLanguageUiAction.ChangeLanguage -> {
                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(
                        languageCode = when (event.languageCode) {
                            LanguageCode.EN -> LanguageCode.EN
                            LanguageCode.VI -> LanguageCode.VI
                        }
                    )
                    appManager.saveLanguageCode(event.languageCode.name.lowercase())
                }
            }
        }
    }
}