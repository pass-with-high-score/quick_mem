package com.pwhs.quickmem.domain.repository

import android.content.Context
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
        fullName: String,
        birthDay: Date,
        role: UserRole
    ): Flow<Resources<Boolean>>

    suspend fun signupWithGoogle(context: Context): Flow<Resources<Boolean>>
}