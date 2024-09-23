package com.pwhs.quickmem.presentation.auth.forgot_password.set_new_password

sealed class SetNewPasswordUiAction {
    data class PasswordChanged(val password: String) : SetNewPasswordUiAction()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SetNewPasswordUiAction()
    data object Submit : SetNewPasswordUiAction()
}
