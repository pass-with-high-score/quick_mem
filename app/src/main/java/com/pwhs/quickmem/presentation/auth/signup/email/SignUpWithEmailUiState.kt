package com.pwhs.quickmem.presentation.auth.signup.email

import com.pwhs.quickmem.core.data.UserRole

data class SignUpWithEmailUiState(
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val birthday: String = "",
    val birthdayError: String = "",
    val userRole: UserRole = UserRole.Student,
    val isLoading: Boolean = false,
)