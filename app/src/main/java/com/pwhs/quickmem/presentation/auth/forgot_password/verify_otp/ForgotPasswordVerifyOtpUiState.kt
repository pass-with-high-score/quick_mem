package com.pwhs.quickmem.presentation.auth.forgot_password.verify_otp

data class ForgotPasswordVerifyOtpUiState(
    val otp: String = "",
    val otpError: String = "",
    val isLoading: Boolean = false,
)
