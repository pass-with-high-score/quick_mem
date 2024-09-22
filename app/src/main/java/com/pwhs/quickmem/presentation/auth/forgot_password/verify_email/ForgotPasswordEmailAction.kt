package com.pwhs.quickmem.presentation.auth.forgot_password.verify_email

sealed class ForgotPasswordVerifyEmailUiAction {
    data class EmailChanged(val email: String) : ForgotPasswordVerifyEmailUiAction()
    data object ResetPassword : ForgotPasswordVerifyEmailUiAction()
}
