package com.pwhs.quickmem.presentation.auth.signup.email

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.data.AuthProvider
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.SignupRequestModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.util.emailIsValid
import com.pwhs.quickmem.util.getNameFromEmail
import com.pwhs.quickmem.util.getUsernameFromEmail
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
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class SignupWithEmailViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(SignUpWithEmailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<SignUpWithEmailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SignUpWithEmailUiAction) {
        when (event) {
            is SignUpWithEmailUiAction.BirthdayChanged -> {
                if (event.birthday.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            birthday = event.birthday,
                            birthdayError = "Birthday is required"
                        )
                    }
                } else {
                    _uiState.update { it.copy(birthday = event.birthday, birthdayError = "") }
                }
            }

            is SignUpWithEmailUiAction.EmailChanged -> {
                if (!event.email.emailIsValid()) {
                    _uiState.update { it.copy(email = event.email, emailError = "Invalid email") }
                } else {
                    _uiState.update { it.copy(email = event.email, emailError = "") }
                }
            }

            is SignUpWithEmailUiAction.PasswordChanged -> {
                if (!event.password.strongPassword()) {
                    _uiState.update {
                        it.copy(
                            password = event.password,
                            passwordError = "Password is too weak!"
                        )
                    }
                } else {
                    _uiState.update { it.copy(password = event.password, passwordError = "") }
                }
            }

            is SignUpWithEmailUiAction.UserRoleChanged -> {
                _uiState.update { it.copy(userRole = event.userRole) }
            }

            is SignUpWithEmailUiAction.SignUp -> {
                if (validateInput()) {
                    signUp()
                } else {
                    Toast.makeText(getApplication(), "Invalid input", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            authRepository.checkEmailValidity(_uiState.value.email).collect { resource ->
                when (resource) {
                    is Resources.Error -> {
                        Timber.e(resource.message)
                        _uiState.update {
                            it.copy(
                                emailError = "Email is already registered",
                                isLoading = false
                            )
                        }
                        _uiEvent.send(SignUpWithEmailUiEvent.SignUpFailure)
                    }

                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        if (resource.data == true) {
                            val avatarUrl = Random().nextInt(18).toString()
                            val username = uiState.value.email.getUsernameFromEmail()
                            val fullName = uiState.value.email.getNameFromEmail()

                            authRepository.signup(
                                signUpRequestModel = SignupRequestModel(
                                    avatarUrl = avatarUrl,
                                    email = uiState.value.email,
                                    username = username,
                                    fullName = fullName,
                                    role = uiState.value.userRole,
                                    birthday = uiState.value.birthday,
                                    password = uiState.value.password,
                                    authProvider = AuthProvider.EMAIL.name
                                )
                            ).collectLatest { signup ->
                                when (signup) {
                                    is Resources.Error -> {
                                        Timber.e(signup.message)
                                        _uiState.update {
                                            it.copy(
                                                isLoading = false,
                                                emailError = "Sign up failed"
                                            )
                                        }
                                        _uiEvent.send(SignUpWithEmailUiEvent.SignUpFailure)
                                    }

                                    is Resources.Loading -> {
                                        // Do nothing
                                    }

                                    is Resources.Success -> {
                                        _uiState.update { it.copy(isLoading = false) }
                                        _uiEvent.send(SignUpWithEmailUiEvent.SignUpSuccess)
                                    }
                                }

                            }
                        } else {
                            _uiEvent.send(SignUpWithEmailUiEvent.SignUpFailure)
                        }
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
        if (uiState.value.birthday.isEmpty()) {
            _uiState.update { it.copy(birthdayError = "Birthday is required") }
            isValid = false
        } else {
            _uiState.update { it.copy(birthdayError = "") }
        }

        return isValid
    }

    override fun onCleared() {
        super.onCleared()
        _uiEvent.close()
    }
}