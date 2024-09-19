package com.pwhs.quickmem.presentation.auth.login


data class LoginWithEmailUiState (
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)