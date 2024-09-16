package com.pwhs.quickmem.domain.model.auth

import com.pwhs.quickmem.core.data.UserRole

data class SignupRequestModel(
    val avatarUrl: String?,
    val email: String?,
    val username: String?,
    val fullName: String?,
    val role: UserRole?,
    val birthday: String?,
    val password: String?
)