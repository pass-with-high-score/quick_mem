package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class VerifyEmailRequestDto (
    @SerializedName("otp")
    val otp: String?,
    @SerializedName("email")
    val email: String?,
)