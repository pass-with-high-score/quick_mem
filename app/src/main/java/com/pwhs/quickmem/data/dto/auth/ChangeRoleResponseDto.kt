package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class ChangeRoleResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("success")
    val success: Boolean
)
