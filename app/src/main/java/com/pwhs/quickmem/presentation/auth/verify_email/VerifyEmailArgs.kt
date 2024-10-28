package com.pwhs.quickmem.presentation.auth.verify_email

data class VerifyEmailArgs(
    val email: String,
    val isFromSignup: Boolean
)