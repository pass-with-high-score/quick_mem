package com.pwhs.quickmem.presentation.auth.verify_email

sealed class VerifyEmailUiEvent {
    data object None : VerifyEmailUiEvent()
    data object VerifySuccess : VerifyEmailUiEvent()
    data object VerifyFailure : VerifyEmailUiEvent()
    data object ResendSuccess : VerifyEmailUiEvent()
    data object ResendFailure : VerifyEmailUiEvent()
}