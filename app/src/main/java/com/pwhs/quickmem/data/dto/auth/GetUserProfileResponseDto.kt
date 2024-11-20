package com.pwhs.quickmem.data.dto.auth

data class GetUserProfileResponseDto(
    val id: String,
    val username: String,
    val fullname: String,
    val email: String,
    val avatarUrl: String,
    val role: String,
    val createdAt: String,
    val updatedAt: String
)
