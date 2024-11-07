package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.AddFoldersToClassRequestDto
import com.pwhs.quickmem.domain.model.classes.AddFoldersToClassRequestModel

fun AddFoldersToClassRequestDto.toModel() = AddFoldersToClassRequestModel(
    classId = classId,
    userId = userId,
    folderIds = folderIds
)

fun AddFoldersToClassRequestModel.toDto() = AddFoldersToClassRequestDto(
    classId = classId,
    userId = userId,
    folderIds = folderIds
)