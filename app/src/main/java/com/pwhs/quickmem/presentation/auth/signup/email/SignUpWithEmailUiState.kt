package com.pwhs.quickmem.presentation.auth.signup.email

import androidx.annotation.StringRes
import com.pwhs.quickmem.core.data.enums.UserRole

data class SignUpWithEmailUiState(
    val email: String = "",
    @StringRes val emailError: Int? = null,
    val password: String = "",
    @StringRes val passwordError: Int? = null,
    val birthday: String = "",
    @StringRes val birthdayError: Int? = null,
    val userRole: UserRole = UserRole.STUDENT,
    val isLoading: Boolean = false,
)