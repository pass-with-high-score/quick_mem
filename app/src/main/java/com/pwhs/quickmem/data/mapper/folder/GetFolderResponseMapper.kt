package com.pwhs.quickmem.data.mapper.folder

import com.pwhs.quickmem.data.dto.folder.GetFolderResponseDto
import com.pwhs.quickmem.data.mapper.study_set.toDto
import com.pwhs.quickmem.data.mapper.study_set.toModel
import com.pwhs.quickmem.data.mapper.user.toDto
import com.pwhs.quickmem.data.mapper.user.toModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel

fun GetFolderResponseDto.toModel() = GetFolderResponseModel(
    id = id,
    title = title,
    description = description,
    isPublic = isPublic,
    studySetCount = studySetCount,
    owner = owner.toModel(),
    studySets = studySets?.map { it.toModel() },
    isImported = isImported,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun GetFolderResponseModel.toDto() = GetFolderResponseDto(
    id = id,
    title = title,
    description = description,
    isPublic = isPublic,
    studySetCount = studySetCount,
    owner = owner.toDto(),
    studySets = studySets?.map { it.toDto() },
    isImported = isImported,
    createdAt = createdAt,
    updatedAt = updatedAt,
)