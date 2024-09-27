package com.pwhs.quickmem.presentation.auth.login

sealed class LoginUiEvent {
    data object NavigateToSignUp : LoginUiEvent()
    data object LoginWithEmail : LoginUiEvent()
    data object LoginWithGoogle : LoginUiEvent()
    data object LoginWithFacebook : LoginUiEvent()
}