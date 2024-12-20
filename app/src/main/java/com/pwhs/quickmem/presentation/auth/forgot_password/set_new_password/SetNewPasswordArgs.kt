package com.pwhs.quickmem.presentation.auth.forgot_password.set_new_password

import kotlinx.serialization.Serializable

@Serializable
data class SetNewPasswordArgs(
    val email: String,
    val otp: String,
    val resetPasswordToken: String,
)