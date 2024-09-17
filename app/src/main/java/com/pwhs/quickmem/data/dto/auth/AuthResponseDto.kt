package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

class AuthResponseDto {
    @SerializedName("id")
    val id: String? = null
    @SerializedName("full_name")
    val fullName: String? = null
    @SerializedName("email")
    val email: String? = null
    @SerializedName("username")
    val username: String? = null
    @SerializedName("role")
    val role: String? = null
    @SerializedName("avatar_url")
    val avatarUrl: String? = null
    @SerializedName("birthday")
    val birthday: String? = null
    @SerializedName("access_token")
    val accessToken: String? = null
    @SerializedName("refresh_token")
    val refreshToken: String? = null
}