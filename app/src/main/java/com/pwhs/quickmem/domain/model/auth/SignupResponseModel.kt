package com.pwhs.quickmem.domain.model.auth

data class SignupResponseModel(
    val message: String,
    val isVerified: Boolean,
    val success: Boolean,
)