package com.pwhs.quickmem.data.mapper.folder

import com.pwhs.quickmem.data.dto.folder.SaveRecentAccessFolderRequestDto
import com.pwhs.quickmem.domain.model.folder.SaveRecentAccessFolderRequestModel

fun SaveRecentAccessFolderRequestDto.toModel() = SaveRecentAccessFolderRequestModel(
    userId = userId,
    folderId = folderId
)

fun SaveRecentAccessFolderRequestModel.toDto() = SaveRecentAccessFolderRequestDto(
    userId = userId,
    folderId = folderId
)
