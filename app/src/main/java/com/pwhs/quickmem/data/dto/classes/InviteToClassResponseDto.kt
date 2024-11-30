package com.pwhs.quickmem.data.dto.classes

import com.google.gson.annotations.SerializedName

data class InviteToClassResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
)
