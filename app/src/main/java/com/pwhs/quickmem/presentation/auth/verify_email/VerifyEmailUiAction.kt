package com.pwhs.quickmem.presentation.auth.verify_email

sealed class VerifyEmailUiAction {
    data class OtpChange(val otp: String) : VerifyEmailUiAction()
    data class EmailChange(val email: String) : VerifyEmailUiAction()
    data object VerifyEmail : VerifyEmailUiAction()
    data object ResendEmail : VerifyEmailUiAction()
}