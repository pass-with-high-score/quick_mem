package com.pwhs.quickmem.data.dto.notification

import com.google.gson.annotations.SerializedName

data class NotificationDataDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("code")
    val code: String? = null,
)
