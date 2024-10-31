package com.pwhs.quickmem.data.mapper.folder

import com.pwhs.quickmem.data.dto.folder.UpdateFolderRequestDto
import com.pwhs.quickmem.domain.model.folder.UpdateFolderRequestModel

fun UpdateFolderRequestModel.toDto() = UpdateFolderRequestDto(
    description = description,
    isPublic = isPublic,
    title = title
)

fun UpdateFolderRequestDto.toModel() = UpdateFolderRequestModel(
    description = description,
    isPublic = isPublic,
    title = title
)