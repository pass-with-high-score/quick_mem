package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.UpdateStudySetResponseDto
import com.pwhs.quickmem.domain.model.study_set.UpdateStudySetResponseModel

fun UpdateStudySetResponseModel.toDto() = UpdateStudySetResponseDto(
    colorId = colorId,
    description = description,
    isPublic = isPublic,
    subjectId = subjectId,
    title = title,
    createdAt = createdAt,
    updatedAt = updatedAt,
    id = id
)

fun UpdateStudySetResponseDto.toModel() = UpdateStudySetResponseModel(
    colorId = colorId,
    description = description,
    isPublic = isPublic,
    subjectId = subjectId,
    title = title,
    createdAt = createdAt,
    updatedAt = updatedAt,
    id = id
)