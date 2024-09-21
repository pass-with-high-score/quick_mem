package com.pwhs.quickmem.presentation.auth.verify_email

import com.pwhs.quickmem.core.data.UserRole

sealed class VerifyEmailUiAction {
    data class OtpChange(val otp: String) : VerifyEmailUiAction()
    data class EmailChange(val email: String) : VerifyEmailUiAction()
    data object VerifyEmail : VerifyEmailUiAction()
}