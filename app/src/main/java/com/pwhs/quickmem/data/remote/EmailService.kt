package com.pwhs.quickmem.data.remote

import com.pwhs.quickmem.data.dto.verify_email.EmailRequestDto
import com.pwhs.quickmem.data.dto.verify_email.EmailVerificationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailService {
    @POST("v0/check_email")
    suspend fun checkEmail(@Body emailRequestDto: EmailRequestDto): EmailVerificationResponse
}
