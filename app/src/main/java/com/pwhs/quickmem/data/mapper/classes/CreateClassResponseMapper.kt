package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.CreateClassResponseDto
import com.pwhs.quickmem.domain.model.classes.CreateClassResponseModel

fun CreateClassResponseDto.toModel() = CreateClassResponseModel(
    id = id,
    title = title,
    description = description,
    joinToken = joinToken,
    allowSetManagement = allowSetManagement,
    allowMemberManagement = allowMemberManagement,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun CreateClassResponseModel.toDto() = CreateClassResponseDto(
    id = id,
    title = title,
    description = description,
    joinToken = joinToken,
    allowSetManagement = allowSetManagement,
    allowMemberManagement = allowMemberManagement,
    createdAt = createdAt,
    updatedAt = updatedAt
)
