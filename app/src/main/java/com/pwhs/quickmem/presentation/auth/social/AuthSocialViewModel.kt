package com.pwhs.quickmem.presentation.auth.social

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.enums.AuthProvider
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.SignupRequestModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.util.getUsernameFromEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthSocialViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthSocialUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<AuthSocialUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AuthSocialUiAction) {
        when (event) {
            is AuthSocialUiAction.OnAvatarUrlChanged -> {
                _uiState.value = _uiState.value.copy(avatarUrl = event.avatarUrl)
            }

            is AuthSocialUiAction.OnBirthDayChanged -> {
                _uiState.value = _uiState.value.copy(birthDay = event.birthDay)
            }

            is AuthSocialUiAction.OnEmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.email)
            }

            is AuthSocialUiAction.OnNameChanged -> {
                _uiState.value = _uiState.value.copy(fullName = event.name)
            }

            is AuthSocialUiAction.OnRoleChanged -> {
                _uiState.value = _uiState.value.copy(role = event.role)
            }

            is AuthSocialUiAction.Register -> {
                if (validateInput()) {
                    authSocial()
                } else {
                    _uiEvent.trySend(AuthSocialUiEvent.SignUpFailure(R.string.txt_invalid_input))
                }
            }
        }
    }

    fun initDataDeeplink(
        email: String,
        fullName: String,
        avatarUrl: String,
        token: String,
        provider: String,
        isSignUp: Boolean
    ) {
        Timber.d(token)
        _uiState.value = AuthSocialUiState(
            email = email,
            fullName = fullName,
            avatarUrl = avatarUrl,
            token = token,
            provider = AuthProvider.valueOf(provider),
            isSignUp = isSignUp
        )
    }

    private fun authSocial() {
        val username = uiState.value.email.getUsernameFromEmail()

        viewModelScope.launch {

            val response = authRepository.signup(
                signUpRequestModel = SignupRequestModel(
                    avatarUrl = uiState.value.avatarUrl,
                    email = uiState.value.email,
                    username = username,
                    fullName = uiState.value.fullName,
                    role = uiState.value.role,
                    birthday = uiState.value.birthDay,
                    authProvider = AuthProvider.GOOGLE.name,
                )
            )

            response.collectLatest { resource ->
                when (resource) {
                    is Resources.Error -> {
                        Timber.e(resource.message)
                        _uiEvent.send(AuthSocialUiEvent.SignUpFailure(R.string.txt_error_occurred))
                    }

                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        _uiEvent.send(AuthSocialUiEvent.SignUpSuccess)
                    }
                }

            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (uiState.value.birthDay.isEmpty()) {
            _uiState.update { it.copy(birthdayError = R.string.txt_birthday_is_required) }
            isValid = false
        } else {
            _uiState.update { it.copy(birthdayError = null) }
        }

        return isValid
    }
}