package com.pwhs.quickmem.data.mapper.notification

import com.pwhs.quickmem.data.dto.notification.NotificationDataDto
import com.pwhs.quickmem.domain.model.notification.NotificationDataModel

fun NotificationDataDto.toModel() = NotificationDataModel(
    id = id,
    code = code
)

fun NotificationDataModel.toDto() = NotificationDataDto(
    id = id,
    code = code
)