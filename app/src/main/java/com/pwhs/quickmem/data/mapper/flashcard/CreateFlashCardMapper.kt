package com.pwhs.quickmem.data.mapper.flashcard

import com.pwhs.quickmem.data.dto.flashcard.FlashCardResponseDto
import com.pwhs.quickmem.data.mapper.option.toDto
import com.pwhs.quickmem.data.mapper.option.toModel
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel

fun FlashCardResponseDto.toModel() = FlashCardResponseModel(
    id = id,
    question = question,
    questionImageURL = questionImageURL,
    answer = answer,
    answerImageURL = answerImageURL,
    hint = hint,
    explanation = explanation,
    option = option?.map { it.toModel() },
    studySetId = studySetId,
    rating = rating,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun FlashCardResponseModel.toDto() = FlashCardResponseDto(
    id = id,
    question = question,
    questionImageURL = questionImageURL,
    answer = answer,
    answerImageURL = answerImageURL,
    hint = hint,
    explanation = explanation,
    option = option?.map { it.toDto() },
    studySetId = studySetId,
    rating = rating,
    createdAt = createdAt,
    updatedAt = updatedAt
)