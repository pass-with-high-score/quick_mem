package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class SendResetPasswordRequestDto(
    @SerializedName("email")
    val email: String
)