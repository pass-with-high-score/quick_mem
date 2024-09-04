package com.pwhs.quickmem.data.dto

import com.pwhs.quickmem.core.data.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id")
    val id: String,
    @SerialName("full_name")
    val fullName: String?,
    @SerialName("avatar_url")
    val avatarUrl: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("user_name")
    val userName: String?,
    @SerialName("role")
    val role: UserRole?,
    @SerialName("birth_day")
    val birthDay: String?,
)