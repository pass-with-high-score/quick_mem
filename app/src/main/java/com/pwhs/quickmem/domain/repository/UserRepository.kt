package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserById(userId: String, token: String): Flow<Resources<UserResponseModel>>
}