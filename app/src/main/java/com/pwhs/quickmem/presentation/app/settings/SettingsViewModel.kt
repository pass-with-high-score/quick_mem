package com.pwhs.quickmem.presentation.app.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val appManager: AppManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<SettingUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        initData()
    }

    fun onEvent(event: SettingUiAction) {
        when (event) {
            SettingUiAction.Logout -> logout()
        }
    }

    private fun initData() {
        viewModelScope.launch {
            try {
                val fullName = appManager.userFullName.firstOrNull() ?: ""
                val username = appManager.userName.firstOrNull() ?: ""
                val email = appManager.userEmail.firstOrNull() ?: ""
                _uiState.update {
                    it.copy(
                        fullName = fullName,
                        username = username,
                        email = email
                    )
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            try {
                tokenManager.clearTokens()
                appManager.clearAllData()
                _uiEvent.send(SettingUiEvent.NavigateToLogin)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}
