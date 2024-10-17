package com.pwhs.quickmem.data.dto.upload

import com.google.gson.annotations.SerializedName

data class UploadImageResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("url")
    val url: String
)