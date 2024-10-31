package com.pwhs.quickmem.data.mapper.folder

import com.pwhs.quickmem.data.dto.folder.GetFolderDetailResponseDto
import com.pwhs.quickmem.data.mapper.study_set.toDto
import com.pwhs.quickmem.data.mapper.study_set.toModel
import com.pwhs.quickmem.data.mapper.user.toDto
import com.pwhs.quickmem.data.mapper.user.toModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel

fun GetFolderDetailResponseDto.toModel() = GetFolderResponseModel(
    id = id,
    title = title,
    description = description,
    isPublic = isPublic,
    studySetCount = studySetCount,
    ownerId = ownerId,
    user = user.toModel(),
    studySets = studySets.map { it.toModel() },
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun GetFolderResponseModel.toDto() = GetFolderDetailResponseDto(
    id = id,
    title = title,
    description = description,
    isPublic = isPublic,
    studySetCount = studySets.size,
    ownerId = ownerId,
    user = user.toDto(),
    studySets = studySets.map { it.toDto() },
    createdAt = createdAt,
    updatedAt = updatedAt,
)