package com.pwhs.quickmem.presentation.auth.signup

sealed class SignupUiEvent {
    data object SignupWithGoogle : SignupUiEvent()
    data object SignupWithFacebook : SignupUiEvent()
}