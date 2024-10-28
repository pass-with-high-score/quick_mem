package com.pwhs.quickmem.presentation.auth.verify_email

sealed class VerifyEmailUiEvent {
    data object VerifySuccess : VerifyEmailUiEvent()
    data object VerifyFailure : VerifyEmailUiEvent()
    data object ResendSuccess : VerifyEmailUiEvent()
    data object ResendFailure : VerifyEmailUiEvent()
    data object WrongOtp : VerifyEmailUiEvent()
    data object EmptyOtp : VerifyEmailUiEvent()
    data object ErrorLengthOtp : VerifyEmailUiEvent()
}