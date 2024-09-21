package com.pwhs.quickmem.presentation.auth.login.email


sealed class LoginWithEmailUiAction {
    data class EmailChanged(val email: String) : LoginWithEmailUiAction()
    data class PasswordChanged(val password: String) : LoginWithEmailUiAction()
    data object Login : LoginWithEmailUiAction()
}