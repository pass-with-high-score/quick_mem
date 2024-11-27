package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.SaveRecentAccessStudySetRequestDto
import com.pwhs.quickmem.domain.model.study_set.SaveRecentAccessStudySetRequestModel

fun SaveRecentAccessStudySetRequestModel.toDto() = SaveRecentAccessStudySetRequestDto(
    userId = userId,
    studySetId = studySetId
)

fun SaveRecentAccessStudySetRequestDto.toModel() = SaveRecentAccessStudySetRequestModel(
    userId = userId,
    studySetId = studySetId
)