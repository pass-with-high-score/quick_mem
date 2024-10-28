package com.pwhs.quickmem.presentation.app.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appManager: AppManager,
    private val tokenManager: TokenManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    private val _emailState = MutableStateFlow("")
    val emailState = _emailState.asStateFlow()
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        onEvent(ProfileUiAction.LoadProfile)
    }

    init {
        loadEmail()
    }

    fun onEvent(event: ProfileUiAction) {
        when (event) {
            ProfileUiAction.LoadProfile -> loadProfile()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val userId = appManager.userId.firstOrNull() ?: return@launch
        }
    }

    private fun logout() {
        viewModelScope.launch {
            appManager.clearAllData()
            tokenManager.clearTokens()
        }
    }

    private fun loadEmail() {
        viewModelScope.launch {
            appManager.saveUserEmail.collectLatest { email ->
                _emailState.value = email
            }
        }
    }
}