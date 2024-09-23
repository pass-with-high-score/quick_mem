package com.pwhs.quickmem.presentation.auth.forgot_password.verify_otp


sealed class ForgotPasswordVerifyOtpUiAction {
    data class OtpChanged(val otp: String) : ForgotPasswordVerifyOtpUiAction()
    data object VerifyOtp : ForgotPasswordVerifyOtpUiAction()
}
