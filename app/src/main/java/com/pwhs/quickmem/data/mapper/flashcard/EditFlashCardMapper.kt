package com.pwhs.quickmem.data.mapper.flashcard

import com.pwhs.quickmem.data.dto.flashcard.EditFlashCardDto
import com.pwhs.quickmem.domain.model.flashcard.EditFlashCardModel

fun EditFlashCardDto.toModel() = EditFlashCardModel(
    term = term,
    definition = definition,
    definitionImageURL = definitionImageURL,
    hint = hint,
    explanation = explanation,
)

fun EditFlashCardModel.toDto() = EditFlashCardDto(
    term = term,
    definition = definition,
    definitionImageURL = definitionImageURL,
    hint = hint,
    explanation = explanation,
)