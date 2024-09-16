package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.UserModel
import com.pwhs.quickmem.core.data.UserRole
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<Resources<UserModel>>
    suspend fun signup(
        email: String,
        userName: String,
        password: String,
        birthDay: Date,
        role: UserRole
    ): Flow<Resources<Boolean>>

}