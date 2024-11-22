package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.DeleteFolderRequestDto
import com.pwhs.quickmem.domain.model.classes.DeleteFolderRequestModel

fun DeleteFolderRequestModel.toDto() = DeleteFolderRequestDto(
    userId = userId,
    folderId = folderId,
    classId = classId
)

fun DeleteFolderRequestDto.toModel() = DeleteFolderRequestModel(
    userId = userId,
    classId = classId,
    folderId = folderId
)