package com.pwhs.quickmem.presentation.auth.signup.email

import com.pwhs.quickmem.core.data.UserRole

data class SignUpWithEmailUiState (
    val email: String = "",
    val password: String = "",
    val birthday: String = "",
    val userRole: UserRole = UserRole.STUDENT,
    val isLoading: Boolean = false,
)