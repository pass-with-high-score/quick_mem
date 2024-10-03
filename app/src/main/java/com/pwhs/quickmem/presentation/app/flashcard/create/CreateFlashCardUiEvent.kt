package com.pwhs.quickmem.presentation.app.flashcard.create

import android.net.Uri

sealed class CreateFlashCardUiEvent {
    data class StudySetIdChanged(val studySetId: String) : CreateFlashCardUiEvent()
    data class StudySetTitleChanged(val studySetTitle: String) : CreateFlashCardUiEvent()
    data class QuestionChanged(val question: String) : CreateFlashCardUiEvent()
    data class QuestionImageUrlChanged(val questionImageUrl: List<String>) :
        CreateFlashCardUiEvent()


    data class AnswerChanged(val answer: String) : CreateFlashCardUiEvent()
    data class AnswerImageUrlChanged(val answerImageUrl: List<String>) : CreateFlashCardUiEvent()
    data object SaveFlashCardClicked : CreateFlashCardUiEvent()
    data object CancelClicked : CreateFlashCardUiEvent()
}