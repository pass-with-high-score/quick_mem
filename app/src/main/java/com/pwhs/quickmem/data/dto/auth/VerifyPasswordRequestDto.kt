package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class VerifyPasswordRequestDto(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("password")
    val password: String
)
