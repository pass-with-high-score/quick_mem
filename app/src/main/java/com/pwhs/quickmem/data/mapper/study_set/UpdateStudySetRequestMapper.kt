package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.UpdateStudySetRequestDto
import com.pwhs.quickmem.domain.model.study_set.UpdateStudySetRequestModel

fun UpdateStudySetRequestModel.toDto() = UpdateStudySetRequestDto(
    colorId = colorId,
    description = description,
    isPublic = isPublic,
    ownerId = ownerId,
    subjectId = subjectId,
    title = title
)

fun UpdateStudySetRequestDto.toModel() = UpdateStudySetRequestModel(
    colorId = colorId,
    description = description,
    isPublic = isPublic,
    ownerId = ownerId,
    subjectId = subjectId,
    title = title
)