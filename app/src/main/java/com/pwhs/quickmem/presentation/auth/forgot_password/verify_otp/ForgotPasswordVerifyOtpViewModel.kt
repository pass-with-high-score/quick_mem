package com.pwhs.quickmem.presentation.auth.forgot_password.verify_otp

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordVerifyOtpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(ForgotPasswordVerifyOtpUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ForgotPasswordVerifyOtpUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ForgotPasswordVerifyOtpUiAction) {
        when (event) {
            is ForgotPasswordVerifyOtpUiAction.OtpChanged -> {
                if (event.otp.isBlank()) {
                    _uiState.update {
                        it.copy(
                            otp = event.otp,
                            otpError = "Code cannot be empty"
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            otp = event.otp,
                            otpError = ""
                        )
                    }
                }
            }

            is ForgotPasswordVerifyOtpUiAction.VerifyOtp -> {
                if (validateInput()) {
                    verifyCode()
                } else {
                    Toast.makeText(getApplication(), "Invalid input", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun verifyCode() {
        _uiEvent.trySend(ForgotPasswordVerifyOtpUiEvent.VerifySuccess)

    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (uiState.value.otp.isBlank()) {
            _uiState.update { it.copy(otpError = "Code cannot be empty") }
            isValid = false
        } else {
            _uiState.update { it.copy(otpError = "") }
        }

        return isValid
    }

    override fun onCleared() {
        super.onCleared()
        _uiEvent.close()
    }
}
