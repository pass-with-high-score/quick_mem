package com.pwhs.quickmem.data.remote.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.data.dto.verify_email.EmailRequestDto
import com.pwhs.quickmem.data.mapper.auth.toDto
import com.pwhs.quickmem.data.mapper.auth.toModel
import com.pwhs.quickmem.data.remote.ApiService
import com.pwhs.quickmem.data.remote.EmailService
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
import com.pwhs.quickmem.domain.model.auth.UpdateEmailRequestModel
import com.pwhs.quickmem.domain.model.auth.UpdateEmailResponseModel
import com.pwhs.quickmem.domain.model.auth.UpdateFullNameRequestModel
import com.pwhs.quickmem.domain.model.auth.UpdateFullNameResponseModel
import com.pwhs.quickmem.domain.model.auth.VerifyEmailResponseModel
import com.pwhs.quickmem.domain.model.auth.VerifyPasswordRequestModel
import com.pwhs.quickmem.domain.model.auth.VerifyPasswordResponseModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val emailService: EmailService
) : AuthRepository {
    override suspend fun checkEmailValidity(email: String): Flow<Resources<Boolean>> {
        return flow {
            try {
                emit(Resources.Loading())
                val emailDto = EmailRequestDto(email)
                val response = emailService.checkEmail(emailDto)
                if (response.isReachable == "safe" || response.isReachable == "risky") {
                    emit(Resources.Success(true))
                } else {
                    emit(Resources.Success(false))
                }
            } catch (e: Exception) {
                Timber.e(e.toString())
                emit(Resources.Error(e.localizedMessage ?: "Error"))
            }
        }
    }

    override suspend fun login(loginRequestModel: LoginRequestModel): Flow<Resources<AuthResponseModel>> {
        return flow {
            try {
                emit(Resources.Loading())
                val params = loginRequestModel.toDto()
                val response = apiService.login(params)
                Timber.d(response.toString())
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e.toString())
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
                Timber.e(e)
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
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun updateFullName(
        token: String,
        updateFullNameRequestModel: UpdateFullNameRequestModel
    ): Flow<Resources<UpdateFullNameResponseModel>> {
        return flow {
            emit(Resources.Loading(true))
            Timber.d("updateFullName: $updateFullNameRequestModel")
            try {
                val response = apiService.updateFullName(
                    token,
                    updateFullNameRequestModel.toDto()
                )
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun updateEmail(
        token: String,
        updateEmailRequestModel: UpdateEmailRequestModel
    ): Flow<Resources<UpdateEmailResponseModel>> {
        return flow {
            try {
                emit(Resources.Loading())
                val response = apiService.updateEmail(token, updateEmailRequestModel.toDto())
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.localizedMessage ?: "Error"))
            }
        }
    }


    override suspend fun sendResetPassword(
        sendResetPasswordRequestModel: SendResetPasswordRequestModel
    ): Flow<Resources<SendResetPasswordResponseModel>> {
        return flow {
            try {
                emit(Resources.Loading())
                val params = sendResetPasswordRequestModel.toDto()
                val response = apiService.sendResetPassword(params)
                emit(Resources.Success(response))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun resetPassword(
        resetPasswordRequestModel: ResetPasswordRequestModel
    ): Flow<Resources<ResetPasswordResponseModel>> {
        return flow {
            try {
                emit(Resources.Loading())
                val params = resetPasswordRequestModel.toDto()
                val response = apiService.resetPassword(params)
                emit(Resources.Success(response))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }

    override suspend fun verifyPassword(
        token: String,
        verifyPasswordRequestModel: VerifyPasswordRequestModel
    ): Flow<Resources<VerifyPasswordResponseModel>> {
        return flow {
            try {
                emit(Resources.Loading())
                val params = verifyPasswordRequestModel.toDto()
                val response = apiService.verifyPassword(token, params)
                emit(Resources.Success(response.toModel()))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resources.Error(e.toString()))
            }
        }
    }
}