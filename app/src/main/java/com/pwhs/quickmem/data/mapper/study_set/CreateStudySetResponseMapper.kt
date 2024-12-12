package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.CreateStudySetResponseDto
import com.pwhs.quickmem.domain.model.study_set.CreateStudySetResponseModel

fun CreateStudySetResponseModel.toDto() = CreateStudySetResponseDto(
    colorId = colorId,
    description = description.trim(),
    isPublic = isPublic,
    subjectId = subjectId,
    title = title.trim(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    isAIGenerated = isAIGenerated,
    id = id
)

fun CreateStudySetResponseDto.toModel() = CreateStudySetResponseModel(
    colorId = colorId,
    description = description.trim(),
    isPublic = isPublic,
    subjectId = subjectId,
    title = title.trim(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    isAIGenerated = isAIGenerated,
    id = id
)