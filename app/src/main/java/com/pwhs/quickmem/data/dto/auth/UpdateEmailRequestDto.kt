package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class UpdateEmailRequestDto(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("email")
    val email: String
)
