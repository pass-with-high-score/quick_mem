package com.pwhs.quickmem.presentation.auth.forgot_password.verify_code


sealed class ForgotPasswordVerifyCodeUiAction {
    data class CodeChanged(val code: String) : ForgotPasswordVerifyCodeUiAction()
    data object VerifyCode : ForgotPasswordVerifyCodeUiAction()
}
