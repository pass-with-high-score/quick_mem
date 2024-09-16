package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.core.data.UserRole

data class SignupRequestDto(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("role")
    val role: UserRole?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("password")
    val password: String?
)