package com.pwhs.quickmem.presentation.auth.social

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AuthSocialViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(AuthSocialUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<AuthSocialUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AuthSocialUiAction) {
        when (event) {
            is AuthSocialUiAction.OnAvatarUrlChanged -> TODO()
            is AuthSocialUiAction.OnBirthDayChanged -> TODO()
            is AuthSocialUiAction.OnEmailChanged -> TODO()
            is AuthSocialUiAction.OnNameChanged -> TODO()
            is AuthSocialUiAction.OnRoleChanged -> TODO()
            AuthSocialUiAction.Register -> TODO()
        }
    }

    fun initDataDeeplink(email: String, name: String, avatarUrl: String, token: String) {
        _uiState.value = AuthSocialUiState(
            email = email,
            name = name,
            avatarUrl = avatarUrl,
            token = token
        )
    }
}