package com.pwhs.quickmem.data.dto.notification

import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("isRead")
    val isRead: Boolean
)
