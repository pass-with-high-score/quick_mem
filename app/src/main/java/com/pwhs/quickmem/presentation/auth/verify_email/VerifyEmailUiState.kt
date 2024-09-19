package com.pwhs.quickmem.presentation.auth.verify_email

data class VerifyEmailUiState(
    val otp: String = "",
    val email: String = ""
)