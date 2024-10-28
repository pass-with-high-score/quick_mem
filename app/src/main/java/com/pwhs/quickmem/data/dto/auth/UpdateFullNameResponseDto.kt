package com.pwhs.quickmem.data.dto.auth

import com.google.gson.annotations.SerializedName

data class UpdateFullNameResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("fullname")
    val fullname: String
)