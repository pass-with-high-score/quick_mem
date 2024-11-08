package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.AddStudySetToFolderRequestDto
import com.pwhs.quickmem.domain.model.study_set.AddStudySetToFolderRequestModel

fun AddStudySetToFolderRequestModel.toDto() = AddStudySetToFolderRequestDto(
    folderId = folderId,
    studySetIds = studySetIds
)

fun AddStudySetToFolderRequestDto.toModel() = AddStudySetToFolderRequestModel(
    folderId = folderId,
    studySetIds = studySetIds
)