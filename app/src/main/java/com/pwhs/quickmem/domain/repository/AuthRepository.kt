package com.pwhs.quickmem.domain.repository

import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.AuthResponseModel
import com.pwhs.quickmem.domain.model.auth.ChangePasswordRequestModel
import com.pwhs.quickmem.domain.model.auth.ChangePasswordResponseModel
import com.pwhs.quickmem.domain.model.auth.LoginRequestModel
import com.pwhs.quickmem.domain.model.auth.OtpResponseModel
import com.pwhs.quickmem.domain.model.auth.ResendEmailRequestModel
import com.pwhs.quickmem.domain.model.auth.ResetPasswordRequestModel
import com.pwhs.quickmem.domain.model.auth.ResetPasswordResponseModel
import com.pwhs.quickmem.domain.model.auth.SendResetPasswordRequestModel
import com.pwhs.quickmem.domain.model.auth.SendResetPasswordResponseModel
import com.pwhs.quickmem.domain.model.auth.SignupRequestModel
import com.pwhs.quickmem.domain.model.auth.SignupResponseModel
import com.pwhs.quickmem.domain.model.auth.UpdateAvatarRequestModel
import com.pwhs.quickmem.domain.model.auth.UpdateAvatarResponseModel
import com.pwhs.quickmem.domain.model.auth.UpdateEmailRequestModel
import com.pwhs.quickmem.domain.model.auth.UpdateEmailResponseModel
import com.pwhs.quickmem.domain.model.auth.UpdateFullNameRequestModel
import com.pwhs.quickmem.domain.model.auth.UpdateFullNameResponseModel
import com.pwhs.quickmem.domain.model.auth.UpdateUsernameRequestModel
import com.pwhs.quickmem.domain.model.auth.UpdateUsernameResponseModel
import com.pwhs.quickmem.domain.model.auth.VerifyEmailResponseModel
import com.pwhs.quickmem.domain.model.auth.VerifyPasswordRequestModel
import com.pwhs.quickmem.domain.model.auth.VerifyPasswordResponseModel
import com.pwhs.quickmem.domain.model.users.SearchUserResponseModel
import com.pwhs.quickmem.domain.model.users.UserDetailResponseModel
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
    ): Flow<Resources<UpdateFullNameResponseModel>>

    suspend fun updateUsername(
        token: String,
        updateUsernameRequestModel: UpdateUsernameRequestModel
    ): Flow<Resources<UpdateUsernameResponseModel>>

    suspend fun updateEmail(
        token: String,
        updateEmailRequestModel: UpdateEmailRequestModel
    ): Flow<Resources<UpdateEmailResponseModel>>

    suspend fun changePassword(
        token: String,
        changePasswordRequestModel: ChangePasswordRequestModel
    ): Flow<Resources<ChangePasswordResponseModel>>

    suspend fun sendResetPassword(
        sendResetPasswordRequestModel: SendResetPasswordRequestModel
    ): Flow<Resources<SendResetPasswordResponseModel>>

    suspend fun resetPassword(
        resetPasswordRequestModel: ResetPasswordRequestModel
    ): Flow<Resources<ResetPasswordResponseModel>>

    suspend fun verifyPassword(
        token: String,
        verifyPasswordRequestModel: VerifyPasswordRequestModel
    ): Flow<Resources<VerifyPasswordResponseModel>>

    suspend fun getUserDetail(
        userId: String,
        token: String,
        isOwner: Boolean
    ): Flow<Resources<UserDetailResponseModel>>

    suspend fun getAvatar(): Flow<Resources<List<String>>>

    suspend fun updateAvatar(
        token: String,
        avatarId: String,
        updateAvatarRequestModel: UpdateAvatarRequestModel
    ): Flow<Resources<UpdateAvatarResponseModel>>

    suspend fun searchUser(
        token: String,
        username: String,
        size: Int?,
        page: Int?
    ): Flow<Resources<List<SearchUserResponseModel>>>
}