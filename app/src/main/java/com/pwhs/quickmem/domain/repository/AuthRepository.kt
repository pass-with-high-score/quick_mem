package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.data.dto.auth.SendResetPasswordRequestDto
import com.pwhs.quickmem.domain.model.auth.AuthResponseModel
import com.pwhs.quickmem.domain.model.auth.LoginRequestModel
import com.pwhs.quickmem.domain.model.auth.OtpResponseModel
import com.pwhs.quickmem.domain.model.auth.ResendEmailRequestModel
import com.pwhs.quickmem.domain.model.auth.ResetPasswordRequestModel
import com.pwhs.quickmem.domain.model.auth.ResetPasswordResponseModel
import com.pwhs.quickmem.domain.model.auth.SendResetPasswordRequestModel
import com.pwhs.quickmem.domain.model.auth.SendResetPasswordResponseModel
import com.pwhs.quickmem.domain.model.auth.SignupRequestModel
import com.pwhs.quickmem.domain.model.auth.SignupResponseModel
import com.pwhs.quickmem.domain.model.auth.UpdateFullNameRequestModel
import com.pwhs.quickmem.domain.model.auth.UpdateFullNameResponseModel
import com.pwhs.quickmem.domain.model.auth.VerifyEmailResponseModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun checkEmailValidity(email: String): Flow<Resources<Boolean>>
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

    suspend fun updateFullName(
        token: String,
        updateFullNameRequestModel: UpdateFullNameRequestModel
    ):Flow<Resources<UpdateFullNameResponseModel>>

    suspend fun sendResetPassword(
        sendResetPasswordRequestModel: SendResetPasswordRequestModel
    ): Flow<Resources<SendResetPasswordResponseModel>>

    suspend fun resetPassword(
        resetPasswordRequestModel: ResetPasswordRequestModel
    ): Flow<Resources<ResetPasswordResponseModel>>
}