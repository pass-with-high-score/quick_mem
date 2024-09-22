package com.pwhs.quickmem.presentation.auth.forgot_password.verify_email

sealed class ForgotPasswordVerifyEmailUiEvent {
    data object None : ForgotPasswordVerifyEmailUiEvent()
    data object ResetSuccess : ForgotPasswordVerifyEmailUiEvent()
    data object ResetFailure : ForgotPasswordVerifyEmailUiEvent()
}
