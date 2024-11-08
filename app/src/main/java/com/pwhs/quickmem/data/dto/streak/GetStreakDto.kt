package com.pwhs.quickmem.data.dto.streak

import com.google.gson.annotations.SerializedName

data class GetStreakDto(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("streaks")
    val streaks: List<StreakDto>
)
