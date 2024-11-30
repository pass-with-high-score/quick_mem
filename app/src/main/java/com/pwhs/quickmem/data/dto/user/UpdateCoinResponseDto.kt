package com.pwhs.quickmem.data.dto.user

import com.google.gson.annotations.SerializedName

data class UpdateCoinResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("coinAction")
    val coinAction: String,
    @SerializedName("coins")
    val coins: Int
)