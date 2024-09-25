package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.CreateStudySetRequestDto
import com.pwhs.quickmem.domain.model.study_set.CreateStudySetRequestModel

fun CreateStudySetRequestModel.toDto() = CreateStudySetRequestDto(
    colorId = colorId,
    description = description,
    isPublic = isPublic,
    ownerId = ownerId,
    subjectId = subjectId,
    title = title
)

fun CreateStudySetRequestDto.toModel() = CreateStudySetRequestModel(
    colorId = colorId,
    description = description,
    isPublic = isPublic,
    ownerId = ownerId,
    subjectId = subjectId,
    title = title
)