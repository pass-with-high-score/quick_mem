package com.pwhs.quickmem.presentation.auth.login.email


data class LoginWithEmailUiState (
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val isLoading: Boolean = false,
    val isVerified: Boolean = false,
)