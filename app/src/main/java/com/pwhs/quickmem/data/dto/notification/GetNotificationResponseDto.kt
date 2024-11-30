package com.pwhs.quickmem.data.dto.notification

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.core.data.enums.NotificationType

data class GetNotificationResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("isRead")
    val isRead: Boolean,
    @SerializedName("notificationType")
    val notificationType: NotificationType? = NotificationType.NONE,
    @SerializedName("data")
    val data: NotificationDataDto? = null,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
