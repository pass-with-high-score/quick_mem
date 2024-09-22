package com.pwhs.quickmem.presentation.auth.forgot_password.verify_code

sealed class ForgotPasswordVerifyCodeUiEvent {
    data object None : ForgotPasswordVerifyCodeUiEvent()
    data object VerifySuccess : ForgotPasswordVerifyCodeUiEvent()
    data object VerifyFailure : ForgotPasswordVerifyCodeUiEvent()
}
