package com.pwhs.quickmem.presentation.auth.login


sealed class LoginWithEmailUiEvent {
    data object None : LoginWithEmailUiEvent()
    data object LoginSuccess : LoginWithEmailUiEvent()
    data object LoginFailure : LoginWithEmailUiEvent()
}