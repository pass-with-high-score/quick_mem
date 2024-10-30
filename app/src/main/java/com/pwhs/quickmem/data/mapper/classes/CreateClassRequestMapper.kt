package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.CreateClassRequestDto
import com.pwhs.quickmem.domain.model.classes.CreateClassRequestModel

fun CreateClassRequestDto.toModel() = CreateClassRequestModel(
    ownerId = ownerId,
    title = title,
    description = description,
    allowSetManagement = allowSetManagement,
    allowMemberManagement = allowMemberManagement
)

fun CreateClassRequestModel.toDto() = CreateClassRequestDto(
    ownerId = ownerId,
    title = title,
    description = description,
    allowSetManagement = allowSetManagement,
    allowMemberManagement = allowMemberManagement
)
