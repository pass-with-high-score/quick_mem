package com.pwhs.quickmem.data.dto.notification

import com.google.gson.annotations.SerializedName

data class TokenRequestDto(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("deviceToken")
    val deviceToken: String
)
