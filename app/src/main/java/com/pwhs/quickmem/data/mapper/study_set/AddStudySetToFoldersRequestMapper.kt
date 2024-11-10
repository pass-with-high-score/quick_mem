package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.AddStudySetToFoldersRequestDto
import com.pwhs.quickmem.domain.model.study_set.AddStudySetToFoldersRequestModel

fun AddStudySetToFoldersRequestModel.toDto() = AddStudySetToFoldersRequestDto(
    studySetId = studySetId,
    folderIds = folderIds
)

fun AddStudySetToFoldersRequestDto.toModel() = AddStudySetToFoldersRequestModel(
    studySetId = studySetId,
    folderIds = folderIds
)