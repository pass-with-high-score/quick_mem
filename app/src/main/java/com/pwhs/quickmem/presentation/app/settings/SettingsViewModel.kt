package com.pwhs.quickmem.presentation.app.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SettingsViewModel @Inject constructor(
    private val appManager: AppManager,
    private val tokenManager: TokenManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    open val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<SettingsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SettingsUiAction) {
        when (event) {
            SettingsUiAction.Logout -> logout()
            SettingsUiAction.DeleteAccount -> deleteAccount()
            SettingsUiAction.GiveFeedback -> _uiEvent.trySend(SettingsUiEvent.ShowFeedbackDialog).isSuccess
            SettingsUiAction.ReportProblem -> _uiEvent.trySend(SettingsUiEvent.ShowReportDialog).isSuccess
        }
    }

    private fun logout() {
        viewModelScope.launch {
            appManager.clearAllData()
            tokenManager.clearTokens()
            _uiEvent.trySend(SettingsUiEvent.ConfirmLogout).isSuccess
        }
    }

    private fun deleteAccount() {
        viewModelScope.launch {
            _uiEvent.trySend(SettingsUiEvent.ConfirmDeleteAccount).isSuccess
        }
    }
}

