package com.pwhs.quickmem.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<SignupUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SignupUiAction) {
        when (event) {
            SignupUiAction.SignupWithFacebook -> signupWithGoogle()
            SignupUiAction.SignupWithGoogle -> signupWithFacebook()
        }
    }

    fun signupWithGoogle() {
        viewModelScope.launch {
            _uiEvent.trySend(SignupUiEvent.SignupWithGoogle)
        }
    }

    fun signupWithFacebook() {
        viewModelScope.launch {
            _uiEvent.trySend(SignupUiEvent.SignupWithFacebook)
        }
    }
}