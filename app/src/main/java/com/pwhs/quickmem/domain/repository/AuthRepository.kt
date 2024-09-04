package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.domain.model.UserRole
import java.util.Date

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(
        email: String,
        userName: String,
        password: String,
        fullName: String,
        birthDay: Date,
        role: UserRole
    ): Boolean
}