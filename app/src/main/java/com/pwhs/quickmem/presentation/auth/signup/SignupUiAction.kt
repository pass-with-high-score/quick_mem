package com.pwhs.quickmem.presentation.auth.signup

sealed class SignupUiAction {
    data object SignupWithGoogle : SignupUiAction()
    data object SignupWithFacebook : SignupUiAction()
}