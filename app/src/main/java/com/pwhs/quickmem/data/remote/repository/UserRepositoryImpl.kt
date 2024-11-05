package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.data.mapper.user.toModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUserById(userId: String, token: String): Flow<Resources<UserResponseModel>> {
        return flow {
            try {
                emit(Resources.Loading())
                val response = apiService.getUserDetail(token, userId)
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }
}
