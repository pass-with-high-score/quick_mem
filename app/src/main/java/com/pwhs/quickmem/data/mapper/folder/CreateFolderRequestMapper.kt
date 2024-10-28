package com.pwhs.quickmem.data.mapper.folder

import com.pwhs.quickmem.data.dto.folder.CreateFolderRequestDto
import com.pwhs.quickmem.domain.model.folder.CreateFolderRequestModel

fun CreateFolderRequestModel.toDto() = CreateFolderRequestDto(
    title = title,
    description = description,
    isPublic = isPublic,
    ownerId = ownerId
)

fun CreateFolderRequestDto.toModel() = CreateFolderRequestModel(
    title = title,
    description = description,
    isPublic = isPublic,
    ownerId = ownerId
)