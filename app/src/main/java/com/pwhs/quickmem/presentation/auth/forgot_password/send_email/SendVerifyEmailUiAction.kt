package com.pwhs.quickmem.presentation.auth.forgot_password.send_email

sealed class SendVerifyEmailUiAction {
    data class EmailChangedAction(val email: String) : SendVerifyEmailUiAction()
    data object ResetPassword : SendVerifyEmailUiAction()
}
