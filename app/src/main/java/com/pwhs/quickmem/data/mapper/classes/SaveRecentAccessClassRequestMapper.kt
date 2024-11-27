package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.SaveRecentAccessClassRequestDto
import com.pwhs.quickmem.domain.model.classes.SaveRecentAccessClassRequestModel

fun SaveRecentAccessClassRequestDto.toModel() = SaveRecentAccessClassRequestModel(
    userId = userId,
    classId = classId
)

fun SaveRecentAccessClassRequestModel.toDto() = SaveRecentAccessClassRequestDto(
    userId = userId,
    classId = classId
)