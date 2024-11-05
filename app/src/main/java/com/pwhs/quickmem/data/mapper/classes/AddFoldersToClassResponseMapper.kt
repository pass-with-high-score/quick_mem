package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.AddFoldersToClassResponseDto
import com.pwhs.quickmem.domain.model.classes.AddFoldersToClassResponseModel

fun AddFoldersToClassResponseDto.toModel() = AddFoldersToClassResponseModel(
    message=message,
    length = length,
    success = success
)

fun AddFoldersToClassResponseModel.toDto() = AddFoldersToClassResponseDto(
    message=message,
    length = length,
    success = success
)