package com.pwhs.quickmem.data.mapper.flashcard

import com.pwhs.quickmem.data.dto.flashcard.CreateFlashCardDto
import com.pwhs.quickmem.domain.model.flashcard.CreateFlashCardModel

fun CreateFlashCardDto.toModel() = CreateFlashCardModel(
    question = question,
    answer = answer,
    answerImageURL = answerImageURL,
    hint = hint,
    explanation = explanation,
    studySetId = studySetId,
    rating = rating
)

fun CreateFlashCardModel.toDto() = CreateFlashCardDto(
    question = question,
    answer = answer,
    answerImageURL = answerImageURL,
    hint = hint,
    explanation = explanation,
    studySetId = studySetId,
    rating = rating
)