package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class GetUserProfileResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("avatarUrl")
    val avatarUrl: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
