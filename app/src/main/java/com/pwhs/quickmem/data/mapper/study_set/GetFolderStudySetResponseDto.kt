package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.FolderStudySetResponseDto
import com.pwhs.quickmem.domain.model.study_set.FolderStudySetResponseModel
import com.pwhs.quickmem.data.mapper.user.toModel
import com.pwhs.quickmem.data.mapper.user.toDto

fun FolderStudySetResponseModel.toDto() = FolderStudySetResponseDto(
    id = id,
    title = title,
    flashcardCount = flashcardCount,
    owner = owner.toDto()
)

fun FolderStudySetResponseDto.toModel() = FolderStudySetResponseModel(
    id = id,
    title = title,
    flashcardCount = flashcardCount,
    owner = owner.toModel()
)