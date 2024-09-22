package com.pwhs.quickmem.presentation.auth.forgot_password.verify_otp

sealed class ForgotPasswordVerifyOtpUiEvent {
    data object None : ForgotPasswordVerifyOtpUiEvent()
    data object VerifySuccess : ForgotPasswordVerifyOtpUiEvent()
    data object VerifyFailure : ForgotPasswordVerifyOtpUiEvent()
}
