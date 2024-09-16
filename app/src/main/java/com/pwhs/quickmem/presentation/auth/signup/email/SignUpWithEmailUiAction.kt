package com.pwhs.quickmem.presentation.auth.signup.email

import com.pwhs.quickmem.core.data.UserRole

sealed class SignUpWithEmailUiAction {
    data class EmailChanged(val email: String) : SignUpWithEmailUiAction()
    data class PasswordChanged(val password: String) : SignUpWithEmailUiAction()
    data class BirthdayChanged(val birthday: String) : SignUpWithEmailUiAction()
    data class UserRoleChanged(val userRole: UserRole) : SignUpWithEmailUiAction()
    data object SignUp : SignUpWithEmailUiAction()
}