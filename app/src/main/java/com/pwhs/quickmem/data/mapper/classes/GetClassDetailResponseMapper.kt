package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.GetClassDetailResponseDto
import com.pwhs.quickmem.data.mapper.folder.toDto
import com.pwhs.quickmem.data.mapper.folder.toModel
import com.pwhs.quickmem.data.mapper.study_set.toDto
import com.pwhs.quickmem.data.mapper.study_set.toModel
import com.pwhs.quickmem.data.mapper.user.toDto
import com.pwhs.quickmem.data.mapper.user.toModel
import com.pwhs.quickmem.domain.model.classes.GetClassDetailResponseModel

fun GetClassDetailResponseDto.toModel() = GetClassDetailResponseModel(
    id = id,
    title = title,
    description = description,
    owner = owner.toModel(),
    joinToken = joinToken,
    createdAt = createdAt,
    updatedAt = updatedAt,
    allowMemberManagement = allowMemberManagement,
    allowSetManagement = allowSetManagement,
    folderCount = folderCount,
    folders = folders?.map { it.toModel() } ?: emptyList(),
    memberCount = memberCount,
    members = members?.map { it.toModel() } ?: emptyList(),
    studySetCount = studySetCount,
    studySets = studySets?.map { it.toModel() } ?: emptyList(),
)


fun GetClassDetailResponseModel.toDto() = GetClassDetailResponseDto(
    id = id,
    title = title,
    description = description,
    owner = owner.toDto(),
    joinToken = joinToken,
    createdAt = createdAt,
    updatedAt = updatedAt,
    allowMemberManagement = allowMemberManagement,
    allowSetManagement = allowSetManagement,
    folderCount = folderCount,
    folders = folders?.map { it.toDto() } ?: emptyList(),
    memberCount = memberCount,
    members = members?.map { it.toDto() } ?: emptyList(),
    studySetCount = studySetCount,
    studySets = studySets?.map { it.toDto() } ?: emptyList(),
)
