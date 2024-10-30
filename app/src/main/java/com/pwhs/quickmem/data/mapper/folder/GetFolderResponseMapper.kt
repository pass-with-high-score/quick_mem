package com.pwhs.quickmem.data.mapper.folder

import com.pwhs.quickmem.data.dto.folder.GetFolderResponseDto
import com.pwhs.quickmem.data.mapper.study_set.toDto
import com.pwhs.quickmem.data.mapper.study_set.toModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.data.mapper.user.toModel
import com.pwhs.quickmem.data.mapper.user.toDto


fun GetFolderResponseDto.toModel() = GetFolderResponseModel(
    id = id,
    title = title,
    description = description,
    isPublic = isPublic,
    studySetCount = studySetCount,
    ownerId = ownerId,
    user = user.toModel(),
    studySets = studySets.map { it.toModel() },
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun GetFolderResponseModel.toDto() = GetFolderResponseDto(
    id = id,
    title = title,
    description = description,
    isPublic = isPublic,
    studySetCount = studySetCount,
    ownerId = ownerId,
    user = user.toDto(),
    studySets = studySets.map { it.toDto() },
    createdAt = createdAt,
    updatedAt = updatedAt
)