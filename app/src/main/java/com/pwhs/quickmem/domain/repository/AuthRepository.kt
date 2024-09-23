package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.AuthResponseModel
import com.pwhs.quickmem.domain.model.auth.LoginRequestModel
import com.pwhs.quickmem.domain.model.auth.OtpResponseModel
import com.pwhs.quickmem.domain.model.auth.ResendEmailRequestModel
import com.pwhs.quickmem.domain.model.auth.SignupRequestModel
import com.pwhs.quickmem.domain.model.auth.SignupResponseModel
import com.pwhs.quickmem.domain.model.auth.VerifyEmailResponseModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(loginRequestModel: LoginRequestModel): Flow<Resources<AuthResponseModel>>
    suspend fun signup(
        signUpRequestModel: SignupRequestModel
    ): Flow<Resources<SignupResponseModel>>

    suspend fun verifyEmail(
        verifyEmailResponseModel: VerifyEmailResponseModel
    ): Flow<Resources<AuthResponseModel>>

    suspend fun resendOtp(
        resendEmailRequestModel: ResendEmailRequestModel
    ): Flow<Resources<OtpResponseModel>>
    suspend fun resendOtp(
        resendEmailRequestModel: ResendEmailRequestModel
    ): Flow<Resources<OtpResponseModel>>
}