package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponseDto(
    @SerializedName("isSet")
    val isSet: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("email")
    val email: String
)