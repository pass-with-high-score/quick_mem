package com.pwhs.quickmem.data.mapper.notification

import com.pwhs.quickmem.data.dto.notification.GetNotificationResponseDto
import com.pwhs.quickmem.domain.model.notification.GetNotificationResponseModel

fun GetNotificationResponseDto.toModel() = GetNotificationResponseModel(
    id = id,
    title = title,
    message = message,
    userId = userId,
    isRead = isRead,
    createdAt = createdAt,
    updatedAt = updatedAt,
    notificationType = notificationType,
    data = data?.toModel()
)

fun GetNotificationResponseModel.toDto() = GetNotificationResponseDto(
    id = id,
    title = title,
    message = message,
    userId = userId,
    isRead = isRead,
    createdAt = createdAt,
    updatedAt = updatedAt,
    notificationType = notificationType,
    data = data?.toDto()
)
