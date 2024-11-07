package com.pwhs.quickmem.data.dto.user

import com.google.gson.annotations.SerializedName

data class ClassMemberDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("avatarUrl")
    val avatarUrl: String,
    @SerializedName("isOwner")
    val isOwner: Boolean,
    @SerializedName("role")
    val role: String,
)