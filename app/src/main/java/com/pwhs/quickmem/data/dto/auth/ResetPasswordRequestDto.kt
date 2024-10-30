package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequestDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("newPassword")
    val newPassword: String,
    @SerializedName("otp")
    val otp: String,
    @SerializedName("resetPasswordToken")
    val resetPasswordToken: String
)