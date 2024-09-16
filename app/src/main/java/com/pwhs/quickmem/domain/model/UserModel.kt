package com.pwhs.quickmem.domain.model

import com.pwhs.quickmem.core.data.UserRole
data class UserModel(
    val id: String,
    val fullName: String,
    val avatarUrl: String,
    val email: String,
    val userName: String,
    val role: UserRole,
    val birthDay: String,
    val createdAt: String,
    val updatedAt: String,
)
