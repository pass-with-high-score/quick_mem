package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.data.mapper.auth.toDto
import com.pwhs.quickmem.data.mapper.auth.toModel
import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.domain.model.auth.AuthResponseModel
import com.pwhs.quickmem.domain.model.auth.LoginRequestModel
import com.pwhs.quickmem.domain.model.auth.OtpResponseModel
import com.pwhs.quickmem.domain.model.auth.ResendEmailRequestModel
import com.pwhs.quickmem.domain.model.auth.SignupRequestModel
import com.pwhs.quickmem.domain.model.auth.SignupResponseModel
import com.pwhs.quickmem.domain.model.auth.VerifyEmailResponseModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Flow<Resources<AuthResponseModel>> {
        return flow {
            try {
                emit(Resources.Loading())
                val params = LoginRequestModel(email, password).toDto()
                val response = apiService.login(params)
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun signup(signUpRequestModel: SignupRequestModel): Flow<Resources<SignupResponseModel>> {
        return flow {
            try {
                emit(Resources.Loading())
                val params = signUpRequestModel.toDto()
                val response = apiService.signUp(params)
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                emit(Resources.Error(e.toString()))
            }

        }
    }

    override suspend fun verifyEmail(
        verifyEmailResponseModel: VerifyEmailResponseModel
    ): Flow<Resources<AuthResponseModel>> {
        return flow {
            try {
                emit(Resources.Loading())
                val params = verifyEmailResponseModel.toDto()
                val response = apiService.verifyEmail(params)
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun resendOtp(
        resendEmailRequestModel: ResendEmailRequestModel
    ): Flow<Resources<OtpResponseModel>> {
        return flow {
            try {
                emit(Resources.Loading())
                val params = resendEmailRequestModel.toDto()
                val response = apiService.resendVerificationEmail(params)
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                emit(Resources.Error(e.toString()))
            }
        }
    }


}