package com.pwhs.quickmem.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginWithEmailViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginWithEmailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoginWithEmailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: LoginWithEmailUiAction) {
        when (event) {
            is LoginWithEmailUiAction.EmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
            }
            is LoginWithEmailUiAction.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }
            is LoginWithEmailUiAction.Login -> {
                login()
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
                        _uiEvent.send(LoginWithEmailUiEvent.LoginSuccess)
                    }
                }
            }
        }
    }
}
