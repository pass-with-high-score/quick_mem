package com.pwhs.quickmem.data.mapper.folder

import com.pwhs.quickmem.data.dto.folder.CreateFolderResponseDto
import com.pwhs.quickmem.domain.model.folder.CreateFolderResponseModel

fun CreateFolderResponseModel.toDto() = CreateFolderResponseDto(
    id = id,
    title = title.trim(),
    description = description.trim(),
    isPublic = isPublic,
    linkShareCode = linkShareCode,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun CreateFolderResponseDto.toModel() = CreateFolderResponseModel(
    id = id,
    title = title.trim(),
    description = description.trim(),
    isPublic = isPublic,
    linkShareCode = linkShareCode,
    createdAt = createdAt,
    updatedAt = updatedAt
)