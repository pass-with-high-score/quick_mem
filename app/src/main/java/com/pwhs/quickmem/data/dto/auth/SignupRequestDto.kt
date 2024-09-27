package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.core.data.UserRole

data class SignupRequestDto(
    @SerializedName("avatarUrl")
    val avatarUrl: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("role")
    val role: UserRole?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("provider")
    val provider: String? = null,
)