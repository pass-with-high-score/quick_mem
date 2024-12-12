package com.pwhs.quickmem.presentation.auth.forgot_password.set_new_password

import androidx.annotation.StringRes

data class SetNewPasswordUiState(
    val password: String = "",
    val confirmPassword: String = "",
    @StringRes val passwordError: Int? = null,
    @StringRes val confirmPasswordError: Int? = null,
    val isLoading: Boolean = false,
    val resetPasswordToken: String = "",
    val email: String = "",
    val otp: String = "",
)
