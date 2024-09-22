package com.pwhs.quickmem.presentation.auth.forgot_password.set_new_password

data class SetNewPasswordUiState(
    val password: String = "",
    val confirmPassword: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = "",
    val isLoading: Boolean = false
)
