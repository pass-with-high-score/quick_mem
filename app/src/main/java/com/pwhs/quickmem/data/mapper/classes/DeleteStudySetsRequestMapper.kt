package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.DeleteStudySetsRequestDto
import com.pwhs.quickmem.domain.model.classes.DeleteStudySetsRequestModel

fun DeleteStudySetsRequestDto.toModel() = DeleteStudySetsRequestModel(
    userId = userId,
    classId = classId,
    studySetId = studySetId
)

fun DeleteStudySetsRequestModel.toDto() = DeleteStudySetsRequestDto(
    userId = userId,
    classId = classId,
    studySetId = studySetId
)