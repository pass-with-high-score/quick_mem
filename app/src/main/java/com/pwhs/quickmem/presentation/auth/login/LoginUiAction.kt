package com.pwhs.quickmem.presentation.auth.login

sealed class LoginUiAction {
    data object LoginWithGoogle : LoginUiAction()
    data object LoginWithFacebook : LoginUiAction()
}