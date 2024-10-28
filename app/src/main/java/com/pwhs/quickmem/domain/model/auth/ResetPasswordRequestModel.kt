package com.pwhs.quickmem.domain.model.auth

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.data.dto.auth.ResetPasswordRequestDto

data class ResetPasswordRequestModel(
    val email: String,
    val newPassword: String,
    val otp: String,
    val resetPasswordToken: String
)