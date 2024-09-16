package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.domain.model.UserModel
import com.pwhs.quickmem.core.data.UserRole
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject
import timber.log.Timber

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun login(email: String, password: String): Flow<Resources<UserModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun signup(
        email: String,
        userName: String,
        password: String,
        birthDay: Date,
        role: UserRole
    ): Flow<Resources<Boolean>> {
        return flow {
            try {

            } catch (e: Exception) {

            }
        }
    }
}