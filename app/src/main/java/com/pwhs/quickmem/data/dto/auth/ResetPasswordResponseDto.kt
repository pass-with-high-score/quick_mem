package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponseDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("isReset")
    val isReset: Boolean,
    @SerializedName("message")
    val message: String
)