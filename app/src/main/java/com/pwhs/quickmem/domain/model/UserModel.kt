package com.pwhs.quickmem.domain.model

import java.util.Date

data class UserModel(
    val id: String,
    val fullName: String,
    val avatarUrl: String,
    val email: String,
    val userName: String,
    val role: UserRole,
    val birthDay: Date,
)
