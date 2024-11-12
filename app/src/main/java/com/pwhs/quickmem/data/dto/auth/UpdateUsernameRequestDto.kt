package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class UpdateUsernameRequestDto(
    @SerializedName("username")
    val username: String,
    @SerializedName("userId")
    val userId: String
)