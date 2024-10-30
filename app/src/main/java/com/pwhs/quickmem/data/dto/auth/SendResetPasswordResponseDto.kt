package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class SendResetPasswordResponseDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("isSent")
    val isSent: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("resetPasswordToken")
    val resetPasswordToken: String
)