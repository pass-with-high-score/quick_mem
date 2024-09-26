package com.pwhs.quickmem.presentation.auth.login.email

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.util.emailIsValid
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
import javax.inject.Inject

@HiltViewModel
class LoginWithEmailViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    application: Application,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(LoginWithEmailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoginWithEmailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: LoginWithEmailUiAction) {
        when (event) {
            is LoginWithEmailUiAction.EmailChanged -> {
                if (!event.email.emailIsValid()) {
                    _uiState.update { it.copy(email = event.email, emailError = "Invalid email") }
                } else {
                    _uiState.update { it.copy(email = event.email, emailError = "") }
                }
            }

            is LoginWithEmailUiAction.PasswordChanged -> {
                if (event.password.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            password = event.password,
                            passwordError = "Password is required"
                        )
                    }
                } else {
                    _uiState.update { it.copy(password = event.password, passwordError = "") }
                }
            }

            is LoginWithEmailUiAction.Login -> {
                if (validateInput()) {
                    login()
                } else {
                    Toast.makeText(getApplication(), "Invalid input", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            val username = uiState.value.email
            val password = uiState.value.password

            val response = authRepository.login(username, password)

            response.collectLatest { resource ->
                when (resource) {
                    is Resources.Error -> {
                        Timber.e(resource.message)
                        _uiEvent.send(LoginWithEmailUiEvent.LoginFailure)
                    }

                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        tokenManager.saveAccessToken(resource.data?.accessToken ?: "")
                        tokenManager.saveRefreshToken(resource.data?.refreshToken ?: "")
                        appManager.saveIsLoggedIn(true)

                        _uiState.update { it.copy(isAccountVerified = resource.data?.isVerified == true) }

                        if (resource.data?.isVerified == true) {
                            _uiEvent.send(LoginWithEmailUiEvent.LoginSuccess)
                        } else {
                            _uiEvent.send(LoginWithEmailUiEvent.NavigateToVerification)
                        }
                    }
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (!uiState.value.email.emailIsValid() || uiState.value.email.isEmpty()) {
            _uiState.update { it.copy(emailError = "Invalid email") }
            isValid = false
        } else {
            _uiState.update { it.copy(emailError = "") }
        }
        if (!uiState.value.password.strongPassword() || uiState.value.password.isEmpty()) {
            _uiState.update { it.copy(passwordError = "Password is too weak!") }
            isValid = false
        } else {
            _uiState.update { it.copy(passwordError = "") }
        }

        return isValid
    }

    fun isAccountVerified(): Boolean {
        return _uiState.value.isAccountVerified
    }
}

