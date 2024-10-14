package com.pwhs.quickmem.presentation.app.flashcard.create

import android.net.Uri


sealed class CreateFlashCardUiAction {
    data class StudySetIdChanged(val studySetId: String) : CreateFlashCardUiAction()
    data class StudySetTitleChanged(val studySetTitle: String) : CreateFlashCardUiAction()
    data class QuestionChanged(val question: String) : CreateFlashCardUiAction()
    data class AnswerChanged(val answer: String) : CreateFlashCardUiAction()
    data class AnswerImageChanged(val answerImage: Uri?) : CreateFlashCardUiAction()
    data object SaveFlashCardClicked : CreateFlashCardUiAction()
    data object CancelClicked : CreateFlashCardUiAction()
    data object AddOptionClicked : CreateFlashCardUiAction()
}