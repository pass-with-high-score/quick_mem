package com.pwhs.quickmem.data.mapper.folder

import com.pwhs.quickmem.data.dto.folder.UpdateFolderResponseDto
import com.pwhs.quickmem.data.mapper.study_set.toDto
import com.pwhs.quickmem.data.mapper.study_set.toModel
import com.pwhs.quickmem.domain.model.folder.UpdateFolderResponseModel

fun UpdateFolderResponseModel.toDto() = UpdateFolderResponseDto(
    id = id,
    title = title,
    description = description,
    isPublic = isPublic,
    ownerId = ownerId,
    studySetCount = studySetCount,
    studySets = studySets.map { it.toDto() },
    updatedAt = updatedAt,
    createdAt = createdAt
)

fun UpdateFolderResponseDto.toModel() = UpdateFolderResponseModel(
    id = id,
    title = title,
    description = description,
    isPublic = isPublic,
    ownerId = ownerId,
    studySetCount = studySetCount,
    studySets = studySets.map { it.toModel() },
    updatedAt = updatedAt,
    createdAt = createdAt
)