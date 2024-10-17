package com.pwhs.quickmem.presentation.app.flashcard.create

import android.net.Uri
import com.pwhs.quickmem.domain.model.flashcard.CreateFlashCardModel

data class CreateFlashCardUiState(
    val studySetId: String = "",
    val term: String = "",
    val definition: String = "",
    val definitionImageUri: Uri? = null,
    val definitionImageURL: String = "",
    val hint: String = "",
    val showHint: Boolean = false,
    val explanation: String = "",
    val showExplanation: Boolean = false,
)

fun CreateFlashCardUiState.toCreateFlashCardModel(): CreateFlashCardModel {
    return CreateFlashCardModel(
        studySetId = studySetId,
        term = term,
        definition = definition,
        definitionImageURL = definitionImageURL,
        hint = hint,
        explanation = explanation
    )
}
