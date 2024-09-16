package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.AuthResponseModel
import com.pwhs.quickmem.domain.model.auth.SignupRequestModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<Resources<AuthResponseModel>>
    suspend fun signup(
        signUpRequestModel: SignupRequestModel
    ): Flow<Resources<AuthResponseModel>>

}