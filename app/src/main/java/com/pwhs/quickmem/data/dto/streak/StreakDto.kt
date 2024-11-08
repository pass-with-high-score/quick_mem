package com.pwhs.quickmem.data.dto.streak

import com.google.gson.annotations.SerializedName

data class StreakDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("streakCount")
    val streakCount: Int,
    @SerializedName("date")
    val date: String,
)
