package com.pwhs.quickmem.presentation.auth.signup.email

import androidx.lifecycle.ViewModel
import com.pwhs.quickmem.core.data.UserRole
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.util.Date
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
                _uiState.update { it.copy(email = event.email) }
            }

            is SignUpWithEmailUiAction.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }

            is SignUpWithEmailUiAction.UserRoleChanged -> {
                _uiState.update { it.copy(userRole = event.userRole) }
            }

            is SignUpWithEmailUiAction.SignUp -> {
                TODO()
            }
        }
    }
}