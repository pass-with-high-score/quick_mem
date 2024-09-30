package com.pwhs.quickmem.data.remote

import com.pwhs.quickmem.data.dto.auth.AuthResponseDto
import com.pwhs.quickmem.data.dto.auth.LoginRequestDto
import com.pwhs.quickmem.data.dto.auth.OtpResponseDto
import com.pwhs.quickmem.data.dto.auth.ResendEmailRequestDto
import com.pwhs.quickmem.data.dto.auth.SignupRequestDto
import com.pwhs.quickmem.data.dto.auth.SignupResponseDto
import com.pwhs.quickmem.data.dto.auth.VerifyEmailRequestDto
import com.pwhs.quickmem.data.dto.study_set.CreateStudySetRequestDto
import com.pwhs.quickmem.data.dto.study_set.CreateStudySetResponseDto
import com.pwhs.quickmem.data.dto.study_set.GetStudySetResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/signup")
    suspend fun signUp(@Body signupRequestDto: SignupRequestDto): SignupResponseDto

    @POST("auth/login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): AuthResponseDto
    suspend fun login()

    @POST("auth/verify-otp")
    suspend fun verifyEmail(@Body verifyEmailRequestDto: VerifyEmailRequestDto): AuthResponseDto

    @POST("auth/resend-verification-email")
    suspend fun resendVerificationEmail(@Body resendEmailRequestDto: ResendEmailRequestDto): OtpResponseDto

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
}