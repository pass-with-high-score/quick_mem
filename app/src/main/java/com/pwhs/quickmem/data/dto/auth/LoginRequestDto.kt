package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?
)
