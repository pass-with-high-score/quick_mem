package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class GetAvatarResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("avatarUrl")
    val url: String
)