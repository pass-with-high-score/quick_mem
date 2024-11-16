package com.pwhs.quickmem.domain.model.notification

data class GetNotificationResponseModel(
    val id: String,
    val title: String,
    val message: String,
    val userId: String,
    val isRead: Boolean,
    val createdAt: String,
    val updatedAt: String
)