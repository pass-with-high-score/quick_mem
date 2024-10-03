package com.pwhs.quickmem.data.mapper.flashcard

import com.pwhs.quickmem.data.dto.flashcard.CreateFlashCardDto
import com.pwhs.quickmem.data.mapper.option.toDto
import com.pwhs.quickmem.data.mapper.option.toModel
import com.pwhs.quickmem.domain.model.flashcard.CreateFlashCardModel

fun CreateFlashCardDto.toModel() = CreateFlashCardModel(
    question = question,
    questionImageURL = questionImageURL,
    answer = answer,
    answerImageURL = answerImageURL,
    hint = hint,
    explanation = explanation,
    option = option?.map { it.toModel() },
    studySetId = studySetId,
    rating = rating
)

fun CreateFlashCardModel.toDto() = CreateFlashCardDto(
    question = question,
    questionImageURL = questionImageURL,
    answer = answer,
    answerImageURL = answerImageURL,
    hint = hint,
    explanation = explanation,
    option = option?.map { it.toDto() },
    studySetId = studySetId,
    rating = rating
)