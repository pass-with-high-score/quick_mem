package com.pwhs.quickmem.data.dto

import com.pwhs.quickmem.core.data.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignupDto(
    @SerialName("id")
    val id: String,
    @SerialName("full_name")
    val fullName: String?,
    @SerialName("avatar_url")
    val avatarUrl: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("username")
    val userName: String?,
    @SerialName("role")
    val role: UserRole?,
    @SerialName("birthday")
    val birthDay: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
)