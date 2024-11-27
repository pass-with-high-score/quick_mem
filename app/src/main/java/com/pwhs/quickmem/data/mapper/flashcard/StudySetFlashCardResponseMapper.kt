package com.pwhs.quickmem.data.mapper.flashcard

import com.pwhs.quickmem.data.dto.flashcard.StudySetFlashCardResponseDto
import com.pwhs.quickmem.domain.model.flashcard.StudySetFlashCardResponseModel

fun StudySetFlashCardResponseDto.toModel() = StudySetFlashCardResponseModel(
    id = id,
    term = term,
    definition = definition,
    definitionImageURL = definitionImageURL,
    hint = hint,
    explanation = explanation,
    rating = rating,
    flipStatus = flipStatus,
    quizStatus = quizStatus,
    trueFalseStatus = trueFalseStatus,
    writeStatus = writeStatus,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isStarred = isStarred,
    isAIGenerated = isAIGenerated
)

fun StudySetFlashCardResponseModel.toDto() = StudySetFlashCardResponseDto(
    id = id,
    term = term,
    definition = definition,
    definitionImageURL = definitionImageURL,
    hint = hint,
    explanation = explanation,
    rating = rating,
    flipStatus = flipStatus,
    quizStatus = quizStatus,
    trueFalseStatus = trueFalseStatus,
    writeStatus = writeStatus,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isStarred = isStarred,
    isAIGenerated = isAIGenerated
)