package com.pwhs.quickmem.presentation.auth.signup.email


sealed class SignUpWithEmailUiEvent {
    data object None : SignUpWithEmailUiEvent()
    data object SignUpSuccess : SignUpWithEmailUiEvent()
    data object SignUpFailure : SignUpWithEmailUiEvent()
}