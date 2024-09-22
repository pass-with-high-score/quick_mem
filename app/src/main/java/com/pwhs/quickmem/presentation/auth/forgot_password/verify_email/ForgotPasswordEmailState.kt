package com.pwhs.quickmem.presentation.auth.forgot_password.verify_email

data class ForgotPasswordVerifyEmailUiState(
    val email: String = "",
    val emailError: String = "",
    val isLoading: Boolean = false
)
