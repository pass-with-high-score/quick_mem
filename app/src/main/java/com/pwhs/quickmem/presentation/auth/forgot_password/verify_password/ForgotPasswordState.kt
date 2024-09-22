package com.pwhs.quickmem.presentation.auth.forgot_password.verify_password

data class ForgotPasswordVerifyPasswordUiState(
    val password: String = "",
    val confirmPassword: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = "",
    val isLoading: Boolean = false
)
