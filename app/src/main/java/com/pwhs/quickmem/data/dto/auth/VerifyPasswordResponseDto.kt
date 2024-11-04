package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class VerifyPasswordResponseDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)