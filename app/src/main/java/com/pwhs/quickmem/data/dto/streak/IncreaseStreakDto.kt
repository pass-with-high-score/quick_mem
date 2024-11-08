package com.pwhs.quickmem.data.dto.streak

import com.google.gson.annotations.SerializedName

data class IncreaseStreakDto(
    @SerializedName("userId")
    val userId: String,
)
