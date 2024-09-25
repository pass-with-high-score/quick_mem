package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class SignupResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("isVerified")
    val isVerified: Boolean,
    @SerializedName("success")
    val success: Boolean,
)