package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("provider")
    val provider: String? = null,
    @SerializedName("idToken")
    val idToken: String? = null,
)
