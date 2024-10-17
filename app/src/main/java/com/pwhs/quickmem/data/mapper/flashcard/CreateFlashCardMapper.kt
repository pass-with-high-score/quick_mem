package com.pwhs.quickmem.data.mapper.flashcard

import com.pwhs.quickmem.data.dto.flashcard.CreateFlashCardDto
import com.pwhs.quickmem.domain.model.flashcard.CreateFlashCardModel

fun CreateFlashCardDto.toModel() = CreateFlashCardModel(
    term = term,
    definition = definition,
    definitionImageURL = definitionImageURL,
    hint = hint,
    explanation = explanation,
    studySetId = studySetId,
)

fun CreateFlashCardModel.toDto() = CreateFlashCardDto(
    term = term,
    definition = definition,
    definitionImageURL = definitionImageURL,
    hint = hint,
    explanation = explanation,
    studySetId = studySetId,
)