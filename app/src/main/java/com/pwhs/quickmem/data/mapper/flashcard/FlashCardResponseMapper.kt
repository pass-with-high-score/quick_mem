package com.pwhs.quickmem.data.mapper.flashcard

import com.pwhs.quickmem.data.dto.flashcard.FlashCardResponseDto
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel

fun FlashCardResponseDto.toModel() = FlashCardResponseModel(
    id = id,
    term = term,
    definition = definition,
    definitionImageURL = definitionImageURL,
    hint = hint,
    explanation = explanation,
    studySetId = studySetId,
    rating = rating,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isStarred = isStarred,
    flipStatus = flipStatus
)

fun FlashCardResponseModel.toDto() = FlashCardResponseDto(
    id = id,
    term = term,
    definition = definition,
    definitionImageURL = definitionImageURL,
    hint = hint,
    explanation = explanation,
    studySetId = studySetId,
    rating = rating,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isStarred = isStarred,
    flipStatus = flipStatus
)