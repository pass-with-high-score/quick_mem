package com.pwhs.quickmem.presentation.auth.forgot_password.verify_code

data class ForgotPasswordVerifyCodeUiState(
    val code: String = "",
    val codeError: String = "",
    val isLoading: Boolean = false,
)
