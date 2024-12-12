package com.pwhs.quickmem.presentation.auth.login.email

import androidx.annotation.StringRes


data class LoginWithEmailUiState(
    val email: String = "",
    @StringRes val emailError: Int? = null,
    val password: String = "",
    @StringRes val passwordError: Int? = null,
    val isLoading: Boolean = false,
)