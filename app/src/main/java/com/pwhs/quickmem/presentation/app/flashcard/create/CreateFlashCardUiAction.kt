package com.pwhs.quickmem.presentation.app.flashcard.create

import android.net.Uri

sealed class CreateFlashCardUiAction {
    data class StudySetIdChanged(val studySetId: String) : CreateFlashCardUiAction()
    data class StudySetTitleChanged(val studySetTitle: String) : CreateFlashCardUiAction()
    data class QuestionChanged(val question: String) : CreateFlashCardUiAction()
    data class QuestionImageUrlChanged(val questionImageUrl: List<String>) :
        CreateFlashCardUiAction()

    data class ImagesChanged(val images: Uri) : CreateFlashCardUiAction()
    data class ImagesRemoved(val images: Uri) : CreateFlashCardUiAction()

    data class AnswerChanged(val answer: String) : CreateFlashCardUiAction()
    data class AnswerImageUrlChanged(val answerImageUrl: List<String>) : CreateFlashCardUiAction()
    data object SaveFlashCardClicked : CreateFlashCardUiAction()
    data object CancelClicked : CreateFlashCardUiAction()
}