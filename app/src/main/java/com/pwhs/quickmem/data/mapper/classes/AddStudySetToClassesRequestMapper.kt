package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.AddStudySetToClassesRequestDto
import com.pwhs.quickmem.domain.model.classes.AddStudySetToClassesRequestModel

fun AddStudySetToClassesRequestModel.toDto() = AddStudySetToClassesRequestModel(
    studySetId = studySetId,
    classIds = classIds
)

fun AddStudySetToClassesRequestDto.toModel() = AddStudySetToClassesRequestModel(
    studySetId = studySetId,
    classIds = classIds
)