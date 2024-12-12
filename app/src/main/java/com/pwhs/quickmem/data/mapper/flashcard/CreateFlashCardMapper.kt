package com.pwhs.quickmem.data.mapper.flashcard

import com.pwhs.quickmem.data.dto.flashcard.CreateFlashCardDto
import com.pwhs.quickmem.domain.model.flashcard.CreateFlashCardModel

fun CreateFlashCardDto.toModel() = CreateFlashCardModel(
    term = term.trim(),
    definition = definition.trim(),
    definitionImageURL = definitionImageURL,
    hint = hint?.trim(),
    explanation = explanation?.trim(),
    studySetId = studySetId,
)

fun CreateFlashCardModel.toDto() = CreateFlashCardDto(
    term = term.trim(),
    definition = definition.trim(),
    definitionImageURL = definitionImageURL,
    hint = hint?.trim(),
    explanation = explanation?.trim(),
    studySetId = studySetId,
)