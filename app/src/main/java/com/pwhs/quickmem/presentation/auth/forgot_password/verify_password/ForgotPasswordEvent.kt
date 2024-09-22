package com.pwhs.quickmem.presentation.auth.forgot_password.verify_password

sealed class ForgotPasswordVerifyPasswordUiEvent {
    data object None : ForgotPasswordVerifyPasswordUiEvent()
    data object ResetSuccess : ForgotPasswordVerifyPasswordUiEvent()
    data object ResetFailure : ForgotPasswordVerifyPasswordUiEvent()
}
