package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.GetClassDetailResponseDto
import com.pwhs.quickmem.domain.model.classes.GetClassDetailResponseModel

fun GetClassDetailResponseDto.toModel() = GetClassDetailResponseModel(
    id = id,
    title = title,
    description = description,
    owner = owner,
    joinToken = joinToken,
    createdAt = createdAt,
    updatedAt = updatedAt,
    allowMemberManagement = allowMemberManagement,
    allowSetManagement = allowSetManagement,
    folderCount = folderCount,
    folders = folders,
    memberCount = memberCount,
    members = members,
    studySetCount = studySetCount,
    studySets = studySets
)


fun GetClassDetailResponseModel.toDto() = GetClassDetailResponseDto(
    id = id,
    title = title,
    description = description,
    owner = owner,
    joinToken = joinToken,
    createdAt = createdAt,
    updatedAt = updatedAt,
    allowMemberManagement = allowMemberManagement,
    allowSetManagement = allowSetManagement,
    folderCount = folderCount,
    folders = folders,
    memberCount = memberCount,
    members = members,
    studySetCount = studySetCount,
    studySets = studySets
)
