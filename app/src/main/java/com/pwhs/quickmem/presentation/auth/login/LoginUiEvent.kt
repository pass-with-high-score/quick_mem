package com.pwhs.quickmem.presentation.auth.login

sealed class LoginUiEvent {
    data object LoginWithGoogle : LoginUiEvent()
    data object LoginWithFacebook : LoginUiEvent()
}