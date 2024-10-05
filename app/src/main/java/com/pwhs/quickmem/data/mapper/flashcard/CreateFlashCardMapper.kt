package com.pwhs.quickmem.data.mapper.flashcard

import com.pwhs.quickmem.data.dto.flashcard.FlashCardResponseDto
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel

fun FlashCardResponseDto.toModel() = FlashCardResponseModel(
    id = id,
    question = question,
    answer = answer,
    answerImageURL = answerImageURL,
    hint = hint,
    explanation = explanation,
    studySetId = studySetId,
    rating = rating,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun FlashCardResponseModel.toDto() = FlashCardResponseDto(
    id = id,
    question = question,
    answer = answer,
    answerImageURL = answerImageURL,
    hint = hint,
    explanation = explanation,
    studySetId = studySetId,
    rating = rating,
    createdAt = createdAt,
    updatedAt = updatedAt
)