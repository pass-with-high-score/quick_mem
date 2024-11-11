package com.pwhs.quickmem.data.mapper.folder

import com.pwhs.quickmem.data.dto.folder.AddFolderToClassRequestDto
import com.pwhs.quickmem.domain.model.folder.AddFolderToClassRequestModel

fun AddFolderToClassRequestModel.toDto() = AddFolderToClassRequestDto(
    userId = userId,
    classId = classId,
    folderIds = folderIds
)

fun AddFolderToClassRequestDto.toModel() = AddFolderToClassRequestModel(
    userId = userId,
    classId = classId,
    folderIds = folderIds
)