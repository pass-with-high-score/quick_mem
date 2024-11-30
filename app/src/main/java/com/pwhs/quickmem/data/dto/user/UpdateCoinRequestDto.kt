package com.pwhs.quickmem.data.dto.user

import com.google.gson.annotations.SerializedName

data class UpdateCoinRequestDto(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("coin")
    val coin: Int,
    @SerializedName("action")
    val action: String
)