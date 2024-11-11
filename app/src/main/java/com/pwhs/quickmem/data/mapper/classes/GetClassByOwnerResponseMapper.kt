package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.GetClassByOwnerResponseDto
import com.pwhs.quickmem.data.mapper.user.toDto
import com.pwhs.quickmem.data.mapper.user.toModel
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel

fun GetClassByOwnerResponseModel.toDto() = GetClassByOwnerResponseDto(
    allowMemberManagement = allowMemberManagement,
    allowSetManagement = allowSetManagement,
    createdAt = createdAt,
    description = description,
    folderCount = folderCount,
    id = id,
    joinToken = joinToken,
    memberCount = memberCount,
    owner = owner.toDto(),
    studySetCount = studySetCount,
    title = title,
    isImported = isImported,
    updatedAt = updatedAt
)

fun GetClassByOwnerResponseDto.toModel() = GetClassByOwnerResponseModel(
    allowMemberManagement = allowMemberManagement,
    allowSetManagement = allowSetManagement,
    createdAt = createdAt,
    description = description,
    folderCount = folderCount,
    id = id,
    joinToken = joinToken,
    memberCount = memberCount,
    owner = owner.toModel(),
    studySetCount = studySetCount,
    title = title,
    isImported = isImported,
    updatedAt = updatedAt
)