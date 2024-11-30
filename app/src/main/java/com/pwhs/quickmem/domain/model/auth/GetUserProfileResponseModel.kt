package com.pwhs.quickmem.domain.model.auth

data class GetUserProfileResponseModel(
    val id: String,
    val username: String,
    val fullname: String,
    val email: String,
    val avatarUrl: String,
    val role: String,
    val coin: Int,
    val createdAt: String,
    val updatedAt: String
)