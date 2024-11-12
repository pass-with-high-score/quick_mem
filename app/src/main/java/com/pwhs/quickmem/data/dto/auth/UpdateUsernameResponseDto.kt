package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class UpdateUsernameResponseDto(
    @SerializedName("username")
    val username: String,

    @SerializedName("message")
    val message: String
)