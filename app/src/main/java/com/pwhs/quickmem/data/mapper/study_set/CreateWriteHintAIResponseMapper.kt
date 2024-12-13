package com.pwhs.quickmem.data.mapper.study_set

import com.pwhs.quickmem.data.dto.study_set.CreateWriteHintAIResponseDto
import com.pwhs.quickmem.domain.model.study_set.CreateWriteHintAIResponseModel

fun CreateWriteHintAIResponseDto.toModel() = CreateWriteHintAIResponseModel(
    flashcardId = flashcardId,
    aiHint = aiHint,
)

fun CreateWriteHintAIResponseModel.toDto() = CreateWriteHintAIResponseDto(
    flashcardId = flashcardId,
    aiHint = aiHint,
)