package com.pwhs.quickmem.presentation.app.flashcard.create

import android.net.Uri


sealed class CreateFlashCardUiAction {
    data class StudySetIdChanged(val studySetId: String) : CreateFlashCardUiAction()
    data class FlashCardTermChanged(val term: String) : CreateFlashCardUiAction()
    data class FlashCardDefinitionChanged(val definition: String) : CreateFlashCardUiAction()
    data class FlashCardDefinitionImageChanged(val definitionImageUri: Uri?) :
        CreateFlashCardUiAction()

    data class FlashCardHintChanged(val hint: String) : CreateFlashCardUiAction()
    data class FlashCardExplanationChanged(val explanation: String) : CreateFlashCardUiAction()
    data object SaveFlashCard : CreateFlashCardUiAction()

    data class ShowHintClicked(val showHint: Boolean) : CreateFlashCardUiAction()
    data class ShowExplanationClicked(val showExplanation: Boolean) : CreateFlashCardUiAction()
    data class UploadImage(val imageUri: Uri) : CreateFlashCardUiAction()
    data class RemoveImage(val imageURL: String) : CreateFlashCardUiAction()
}