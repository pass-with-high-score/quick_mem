package com.pwhs.quickmem.presentation.auth.forgot_password.verify_password

sealed class ForgotPasswordVerifyPasswordUiAction {
    data class PasswordChanged(val password: String) : ForgotPasswordVerifyPasswordUiAction()
    data class ConfirmPasswordChanged(val confirmPassword: String) : ForgotPasswordVerifyPasswordUiAction()
    data object Submit : ForgotPasswordVerifyPasswordUiAction()
}
