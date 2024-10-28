package com.pwhs.quickmem.presentation.auth.login.email


sealed class LoginWithEmailUiEvent {
    data object LoginSuccess : LoginWithEmailUiEvent()
    data object LoginFailure : LoginWithEmailUiEvent()
    data object NavigateToVerifyEmail : LoginWithEmailUiEvent()
}