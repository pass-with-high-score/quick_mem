package com.pwhs.quickmem.data.mapper.flashcard

import com.pwhs.quickmem.data.dto.flashcard.UpdateFlashCardResponseDto
import com.pwhs.quickmem.domain.model.flashcard.UpdateFlashCardResponseModel

fun UpdateFlashCardResponseDto.toModel() = UpdateFlashCardResponseModel(
    id = id,
    message = message,
    isStarred = isStarred,
    rating = rating,
    flipStatus = flipStatus,
)

fun UpdateFlashCardResponseModel.toDto() = UpdateFlashCardResponseDto(
    id = id,
    message = message,
    isStarred = isStarred,
    rating = rating,
    flipStatus = flipStatus,
)