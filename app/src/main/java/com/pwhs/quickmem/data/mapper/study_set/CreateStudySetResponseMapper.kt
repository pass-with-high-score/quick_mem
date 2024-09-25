package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.CreateStudySetResponseDto
import com.pwhs.quickmem.domain.model.study_set.CreateStudySetResponseModel

fun CreateStudySetResponseModel.toDto() = CreateStudySetResponseDto(
    colorId = colorId,
    description = description,
    isPublic = isPublic,
    ownerId = ownerId,
    subjectId = subjectId,
    title = title,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun CreateStudySetResponseDto.toModel() = CreateStudySetResponseModel(
    colorId = colorId,
    description = description,
    isPublic = isPublic,
    ownerId = ownerId,
    subjectId = subjectId,
    title = title,
    createdAt = createdAt,
    updatedAt = updatedAt
)