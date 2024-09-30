package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("fullName")
    val fullName: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("role")
    val role: String? = null,
    @SerializedName("avatarUrl")
    val avatarUrl: String? = null,
    @SerializedName("birthday")
    val birthday: String? = null,
    @SerializedName("accessToken")
    val accessToken: String? = null,
    @SerializedName("refreshToken")
    val refreshToken: String? = null,
    @SerializedName("provider")
    val provider: String? = null,
    @SerializedName("isVerified")
    val isVerified: Boolean? = null,
)