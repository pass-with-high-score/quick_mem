package com.pwhs.quickmem.presentation.auth.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.data.LanguageCode
import com.pwhs.quickmem.core.datastore.AppManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val appManager: AppManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: WelcomeUiAction) {
        when (event) {
            is WelcomeUiAction.ChangeLanguage -> {
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