package com.pwhs.quickmem.presentation.auth.login.email

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.data.AuthProvider
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.LoginRequestModel
import com.pwhs.quickmem.domain.model.auth.ResendEmailRequestModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.util.emailIsValid
import com.pwhs.quickmem.util.strongPassword
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
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
            val email = uiState.value.email
            authRepository.checkEmailValidity(email).collect { resource ->
                when (resource) {
                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(
                                emailError = "Email is not registered",
                                isLoading = false
                            )
                        }
                        _uiEvent.send(LoginWithEmailUiEvent.LoginFailure)
                    }

                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        if (resource.data == true) {
                            val password = uiState.value.password
                            val provider = AuthProvider.EMAIL.name
                            val loginRequestModel =
                                LoginRequestModel(email, password, provider, null)
                            authRepository.login(loginRequestModel).collectLatest { login ->
                                when (login) {
                                    is Resources.Error -> {
                                        Timber.e(login.message)
                                        _uiState.update {
                                            it.copy(
                                                isLoading = false,
                                                emailError = "Invalid email or password",
                                                passwordError = "Invalid email or password"
                                            )
                                        }
                                        _uiEvent.send(LoginWithEmailUiEvent.LoginFailure)
                                    }

                                    is Resources.Loading -> {
                                        // Show loading
                                    }

                                    is Resources.Success -> {
                                        if (login.data?.isVerified == false) {
                                            checkAccountVerification().also {
                                                _uiEvent.send(LoginWithEmailUiEvent.NavigateToVerifyEmail)
                                            }
                                        } else {
                                            tokenManager.saveAccessToken(
                                                login.data?.accessToken ?: ""
                                            )
                                            tokenManager.saveRefreshToken(
                                                login.data?.refreshToken ?: ""
                                            )
                                            appManager.saveIsLoggedIn(true)
                                            appManager.saveUserId(login.data?.id ?: "")
                                            appManager.saveUserAvatar(login.data?.avatarUrl ?: "")
                                            appManager.saveUserFullName(login.data?.fullName ?: "")
                                            appManager.saveUserEmail(login.data?.email ?: "")
                                            appManager.saveUserName(login.data?.username ?: "")
                                            _uiState.update { it.copy(isLoading = false) }
                                            _uiEvent.send(LoginWithEmailUiEvent.LoginSuccess)
                                        }
                                    }
                                }
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    emailError = "Email is not registered",
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    private fun checkAccountVerification() {
        viewModelScope.launch {
            val email = uiState.value.email
            authRepository.resendOtp(
                ResendEmailRequestModel(
                    email = email
                )
            ).collectLatest { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        // Show loading
                    }

                    is Resources.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _uiEvent.send(LoginWithEmailUiEvent.NavigateToVerifyEmail)
                    }

                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _uiEvent.send(LoginWithEmailUiEvent.LoginFailure)
                    }
                }
            }
        }
    }


    private fun validateInput(): Boolean {
        var isValid = true

        if (!uiState.value.email.validEmail() || uiState.value.email.isEmpty()) {
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
}
