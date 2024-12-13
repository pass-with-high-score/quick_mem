package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.CreateWriteHintAIRequestDto
import com.pwhs.quickmem.domain.model.study_set.CreateWriteHintAIRequestModel

fun CreateWriteHintAIRequestDto.toModel() = CreateWriteHintAIRequestModel(
    flashcardId = flashcardId,
    studySetTitle = studySetTitle,
    studySetDescription = studySetDescription,
    question = question,
    answer = answer,
)

fun CreateWriteHintAIRequestModel.toDto() = CreateWriteHintAIRequestDto(
    flashcardId = flashcardId,
    studySetTitle = studySetTitle,
    studySetDescription = studySetDescription,
    question = question,
    answer = answer,
)