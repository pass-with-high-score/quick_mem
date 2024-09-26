package com.pwhs.quickmem.presentation.auth.login.email


sealed class LoginWithEmailUiEvent {
    data object None : LoginWithEmailUiEvent()
    data object LoginSuccess : LoginWithEmailUiEvent()
    data object LoginFailure : LoginWithEmailUiEvent()
    data object VerificationNotVerified : LoginWithEmailUiEvent()
    data object NavigateToVerification : LoginWithEmailUiEvent()
}