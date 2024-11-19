package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.ExitClassRequestDto
import com.pwhs.quickmem.domain.model.classes.ExitClassRequestModel

fun ExitClassRequestModel.toDto() = ExitClassRequestDto(
    userId = userId,
    classId = classId
)

fun ExitClassRequestDto.toModel() = ExitClassRequestModel(
    userId = userId,
    classId = classId
)