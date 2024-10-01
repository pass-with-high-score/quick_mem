package com.pwhs.quickmem.presentation.auth.verify_email

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.ResendEmailRequestModel
import com.pwhs.quickmem.domain.model.auth.VerifyEmailResponseModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.util.emailIsValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    stateHandle: SavedStateHandle,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    application: Application
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(VerifyEmailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<VerifyEmailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val email = stateHandle.get<String>("email") ?: ""
        _uiState.update { it.copy(email = email) }
        updateCountdown()
    }

    fun onEvent(event: VerifyEmailUiAction) {
        when (event) {
            is VerifyEmailUiAction.EmailChange -> {
                if (!event.email.emailIsValid()) {
                    _uiState.update { it.copy(email = event.email) }
                } else {
                    _uiState.update { it.copy(email = event.email) }
                }
            }

            is VerifyEmailUiAction.OtpChange -> {
                _uiState.update { it.copy(otp = event.otp) }
            }

            is VerifyEmailUiAction.VerifyEmail -> {
                verifyEmail()
            }

            is VerifyEmailUiAction.ResendEmail -> {
                _uiState.update { it.copy(countdown = 60) }
                resendOtp(event.email)
            }
        }
    }

    private fun updateCountdown() {
        viewModelScope.launch {
            _uiState.update { it.copy(countdown = 60) }
            while (_uiState.value.countdown > 0) {
                delay(1000)
                _uiState.update { it.copy(countdown = _uiState.value.countdown - 1) }
            }
        }
    }

    private fun verifyEmail() {
        viewModelScope.launch {
            val email = uiState.value.email
            val otp = uiState.value.otp

            if (email.isEmpty() || otp.isEmpty()) {
                _uiEvent.send(VerifyEmailUiEvent.EmptyOtp)
                return@launch
            }

            if (!isOtpLengthValid(otp)) {
                _uiEvent.send(VerifyEmailUiEvent.ErrorLengthOtp)
                return@launch
            }

            if (!isOtpNumber(otp)) {
                _uiEvent.send(VerifyEmailUiEvent.WrongOtp)
                return@launch
            }

            val response = authRepository.verifyEmail(
                VerifyEmailResponseModel(
                    email = email,
                    otp = otp
                )
            )

            response.collectLatest { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        // Show loading
                    }

                    is Resources.Success -> {
                        tokenManager.saveAccessToken(resource.data?.accessToken ?: "")
                        tokenManager.saveRefreshToken(resource.data?.refreshToken ?: "")
                        appManager.saveUserId(resource.data?.id ?: "")
                        appManager.saveIsLoggedIn(true)
                        _uiEvent.send(VerifyEmailUiEvent.VerifySuccess)
                    }

                    is Resources.Error -> {
                        Timber.e(resource.message)
                        _uiEvent.send(VerifyEmailUiEvent.VerifyFailure)
                    }
                }
            }
        }
    }

    private fun resendOtp(email: String) {
        viewModelScope.launch {
            val response = authRepository.resendOtp(
                ResendEmailRequestModel(
                    email = email
                )
            )

            response.collectLatest { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        // Show loading
                    }

                    is Resources.Success -> {
                        _uiEvent.send(VerifyEmailUiEvent.ResendSuccess)
                    }

                    is Resources.Error -> {
                        Timber.e(resource.message)
                        _uiEvent.send(VerifyEmailUiEvent.ResendFailure)
                    }
                }
            }
        }
    }

    private fun isOtpNumber(otp: String): Boolean {
        return otp.all { it.isDigit() }
    }

    private fun isOtpLengthValid(otp: String): Boolean {
        return otp.length == 6
    }

}