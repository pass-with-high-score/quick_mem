package com.pwhs.quickmem.data.mapper.subject

import com.pwhs.quickmem.data.dto.subject.GetTop5SubjectResponseDto
import com.pwhs.quickmem.domain.model.subject.GetTop5SubjectResponseModel

fun GetTop5SubjectResponseModel.toDto() = GetTop5SubjectResponseDto(
    id = id,
    name = name,
    studySetCount = studySetCount
)

fun GetTop5SubjectResponseDto.toModel() = GetTop5SubjectResponseModel(
    id = id,
    name = name,
    studySetCount = studySetCount
)