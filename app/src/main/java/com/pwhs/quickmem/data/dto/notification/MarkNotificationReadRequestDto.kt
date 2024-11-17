package com.pwhs.quickmem.data.dto.notification

import com.google.gson.annotations.SerializedName

data class MarkNotificationReadRequestDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("isRead")
    val isRead: Boolean
)
