package com.pwhs.quickmem.presentation.auth.signup.email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.SignupRequestModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.util.emailIsValid
import com.pwhs.quickmem.util.getNameFromEmail
import com.pwhs.quickmem.util.getUsernameFromEmail
import com.pwhs.quickmem.util.strongPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class SignupWithEmailViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpWithEmailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<SignUpWithEmailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SignUpWithEmailUiAction) {
        when (event) {
            is SignUpWithEmailUiAction.BirthdayChanged -> {
                _uiState.update { it.copy(birthday = event.birthday) }
            }

            is SignUpWithEmailUiAction.EmailChanged -> {
                if (!event.email.emailIsValid()) {
                    _uiState.update { it.copy(emailError = "Invalid email") }
                    _uiState.update { it.copy(email = event.email) }
                } else {
                    _uiState.update { it.copy(emailError = "") }
                    _uiState.update { it.copy(email = event.email) }
                }
            }

            is SignUpWithEmailUiAction.PasswordChanged -> {
                if (!event.password.strongPassword()) {
                    _uiState.update { it.copy(passwordError = "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character") }
                    _uiState.update { it.copy(password = event.password) }
                } else {
                    _uiState.update { it.copy(passwordError = "") }
                    _uiState.update { it.copy(password = event.password) }
                }
            }

            is SignUpWithEmailUiAction.UserRoleChanged -> {
                _uiState.update { it.copy(userRole = event.userRole) }
            }

            is SignUpWithEmailUiAction.SignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            val avatarUrl = Random().nextInt(100).toString()
            val username = uiState.value.email.getUsernameFromEmail()
            val fullName = uiState.value.email.getNameFromEmail()
            val response = authRepository.signup(
                signUpRequestModel = SignupRequestModel(
                    avatarUrl = avatarUrl,
                    email = uiState.value.email,
                    username = username,
                    fullName = fullName,
                    role = uiState.value.userRole,
                    birthday = uiState.value.birthday,
                    password = uiState.value.password
                )
            )

            response.collectLatest { resource ->
                when (resource) {
                    is Resources.Error -> {
                        Timber.e(resource.message)
                        _uiEvent.send(SignUpWithEmailUiEvent.SignUpFailure)
                    }

                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        _uiEvent.send(SignUpWithEmailUiEvent.SignUpSuccess)
                    }
                }

            }
        }
    }
}