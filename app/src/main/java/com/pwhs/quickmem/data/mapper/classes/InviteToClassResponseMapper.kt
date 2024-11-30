package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.InviteToClassResponseDto
import com.pwhs.quickmem.domain.model.classes.InviteToClassResponseModel

fun InviteToClassResponseDto.toModel() = InviteToClassResponseModel(
    message = message,
    status = status
)

fun InviteToClassResponseModel.toDto() = InviteToClassResponseDto(
    message = message,
    status = status
)