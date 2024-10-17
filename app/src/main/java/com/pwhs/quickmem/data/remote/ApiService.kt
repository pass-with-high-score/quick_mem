package com.pwhs.quickmem.data.remote

import com.pwhs.quickmem.data.dto.auth.AuthResponseDto
import com.pwhs.quickmem.data.dto.auth.LoginRequestDto
import com.pwhs.quickmem.data.dto.auth.OtpResponseDto
import com.pwhs.quickmem.data.dto.auth.ResendEmailRequestDto
import com.pwhs.quickmem.data.dto.auth.SignupRequestDto
import com.pwhs.quickmem.data.dto.auth.SignupResponseDto
import com.pwhs.quickmem.data.dto.auth.VerifyEmailRequestDto
import com.pwhs.quickmem.data.dto.flashcard.CreateFlashCardDto
import com.pwhs.quickmem.data.dto.flashcard.FlashCardResponseDto
import com.pwhs.quickmem.data.dto.study_set.CreateStudySetRequestDto
import com.pwhs.quickmem.data.dto.study_set.CreateStudySetResponseDto
import com.pwhs.quickmem.data.dto.study_set.GetStudySetResponseDto
import com.pwhs.quickmem.data.dto.upload.UploadImageResponseDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("auth/signup")
    suspend fun signUp(@Body signupRequestDto: SignupRequestDto): SignupResponseDto

    @POST("auth/login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): AuthResponseDto

    @POST("auth/verify-otp")
    suspend fun verifyEmail(@Body verifyEmailRequestDto: VerifyEmailRequestDto): AuthResponseDto

    @POST("auth/resend-verification-email")
    suspend fun resendVerificationEmail(@Body resendEmailRequestDto: ResendEmailRequestDto): OtpResponseDto

    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Header("Authorization") authToken: String,
        @Part flashcard: MultipartBody.Part
    ):  UploadImageResponseDto

    @POST("study-set")
    suspend fun createStudySet(
        @Header("Authorization") token: String,
        @Body createStudySetRequestDto: CreateStudySetRequestDto
    ): CreateStudySetResponseDto

    @GET("study-set/{id}")
    suspend fun getStudySetById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): GetStudySetResponseDto

    @GET("study-set/owner/{ownerId}")
    suspend fun getStudySetsByOwnerId(
        @Header("Authorization") token: String,
        @Path("ownerId") ownerId: String
    ): List<GetStudySetResponseDto>

    @POST("flashcard")
    suspend fun createFlashCard(
        @Header("Authorization") token: String,
        @Body createFlashCardDto: CreateFlashCardDto
    ): FlashCardResponseDto
}