package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class UpdateEmailResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("email")
    val email: String
)
