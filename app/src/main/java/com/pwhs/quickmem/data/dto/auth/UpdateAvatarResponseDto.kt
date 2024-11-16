package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class UpdateAvatarResponseDto(
    @SerializedName("message")
    val message:String,
    @SerializedName("avatarUrl")
    val avatarUrl:String
)