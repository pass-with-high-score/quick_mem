package com.pwhs.quickmem.presentation.auth.forgot_password.send_email

sealed class SendVerifyEmailUiEvent {
    data object None : SendVerifyEmailUiEvent()
    data object SendEmailSuccess : SendVerifyEmailUiEvent()
    data object SendEmailFailure : SendVerifyEmailUiEvent()
}
