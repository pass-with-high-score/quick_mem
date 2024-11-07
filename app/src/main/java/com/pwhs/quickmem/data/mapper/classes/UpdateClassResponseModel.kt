package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.UpdateClassResponseDto
import com.pwhs.quickmem.domain.model.classes.UpdateClassResponseModel

fun UpdateClassResponseDto.toModel() = UpdateClassResponseModel(
    id = id,
    title = title,
    description = description,
    joinToken = joinToken,
    allowSetManagement = allowSetManagement,
    allowMemberManagement = allowMemberManagement,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun UpdateClassResponseModel.toDto() = UpdateClassResponseDto(
    id = id,
    title = title,
    description = description,
    joinToken = joinToken,
    allowSetManagement = allowSetManagement,
    allowMemberManagement = allowMemberManagement,
    createdAt = createdAt,
    updatedAt = updatedAt
)