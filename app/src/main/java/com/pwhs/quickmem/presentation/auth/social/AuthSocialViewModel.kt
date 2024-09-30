package com.pwhs.quickmem.presentation.auth.social

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.data.AuthProvider
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.LoginRequestModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthSocialViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
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
            is AuthSocialUiAction.Register -> {
                authSocial()
            }
        }
    }

    fun initDataDeeplink(
        email: String,
        fullName: String,
        avatarUrl: String,
        token: String,
        provider: String
    ) {
        Timber.d(token)
        _uiState.value = AuthSocialUiState(
            email = email,
            fullName = fullName,
            avatarUrl = avatarUrl,
            token = token,
            provider = AuthProvider.valueOf(provider)
        )
    }

    private fun authSocial() {
        val loginRequestModel = _uiState.value.run {
            LoginRequestModel(
                email = email,
                password = null,
                authProvider = provider?.name,
                idToken = token
            )
        }

        viewModelScope.launch {
            authRepository.login(loginRequestModel).collect { result ->
                when (result) {
                    is Resources.Success -> {
                        Timber.d(result.data.toString())
                    }

                    is Resources.Error -> {
                        Timber.e(result.message)
                    }

                    is Resources.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)

                    }
                }
            }
        }
    }
}