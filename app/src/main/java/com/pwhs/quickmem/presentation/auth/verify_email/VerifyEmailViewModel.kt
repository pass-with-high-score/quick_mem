package com.pwhs.quickmem.presentation.auth.verify_email

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.ResendEmailRequestModel
import com.pwhs.quickmem.domain.model.auth.VerifyEmailResponseModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.util.emailIsValid
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
class VerifyEmailViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(VerifyEmailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<VerifyEmailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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
                resendOtp(event.email)
            }
        }
    }

    fun verifyEmail() {
        viewModelScope.launch {
            var response = authRepository.verifyEmail(
                VerifyEmailResponseModel(
                    email = uiState.value.email,
                    otp = uiState.value.otp
                )
            )

            response.collectLatest { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        // Show loading
                    }

                    is Resources.Success -> {
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
            var response = authRepository.resendOtp(
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

}