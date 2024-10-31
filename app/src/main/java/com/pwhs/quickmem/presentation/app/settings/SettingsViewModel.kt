package com.pwhs.quickmem.presentation.app.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiEvent = Channel<SettingUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SettingUiAction) {
        when (event) {
            SettingUiAction.Logout -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            try {
                tokenManager.clearTokens()
                _uiEvent.send(SettingUiEvent.NavigateToLogin)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}
