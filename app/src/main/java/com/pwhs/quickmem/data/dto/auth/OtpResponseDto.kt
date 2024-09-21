package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

class OtpResponseDto {
    @SerializedName("otp")
    val otp: String? = null
    @SerializedName("email")
    val email: String? = null
}