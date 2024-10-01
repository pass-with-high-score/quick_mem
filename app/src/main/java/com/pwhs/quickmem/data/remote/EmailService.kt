package com.pwhs.quickmem.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailService {
    @POST("v0/check_email")
    suspend fun checkEmail(@Body emailRequestDto: EmailRequestDto): EmailVerificationResponse
}

data class EmailRequestDto(
    @SerializedName("to_email")
    val toEmail: String
)

data class EmailVerificationResponse(
    @SerializedName("input") val input: String,
    @SerializedName("is_reachable") val isReachable: String,
)
