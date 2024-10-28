package com.pwhs.quickmem.presentation.auth.forgot_password.send_email

data class SendVerifyEmailUiState(
    val email: String = "",
    val emailError: String = "",
    val isLoading: Boolean = false,
    val resetPasswordToken: String = ""
)
