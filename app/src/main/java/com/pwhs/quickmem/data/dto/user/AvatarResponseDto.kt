package com.pwhs.quickmem.data.dto.user

import com.google.gson.annotations.SerializedName

data class AvatarResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String
)
