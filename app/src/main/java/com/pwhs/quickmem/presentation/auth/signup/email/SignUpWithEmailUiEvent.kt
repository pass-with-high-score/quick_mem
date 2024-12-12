package com.pwhs.quickmem.presentation.auth.signup.email

import androidx.annotation.StringRes


sealed class SignUpWithEmailUiEvent {
    data object SignUpSuccess : SignUpWithEmailUiEvent()
    data class SignUpFailure(@StringRes val message: Int) : SignUpWithEmailUiEvent()
}