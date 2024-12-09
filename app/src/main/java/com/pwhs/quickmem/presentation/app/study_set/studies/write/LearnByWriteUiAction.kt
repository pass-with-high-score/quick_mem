package com.pwhs.quickmem.presentation.app.study_set.studies.write

import com.pwhs.quickmem.core.data.enums.WriteStatus

sealed class LearnByWriteUiAction {
    data object LoadNextFlashCard : LearnByWriteUiAction()
    data class OnAnswer(
        val flashcardId: String,
        val writeStatus: WriteStatus,
        val userAnswer: String,
    ) : LearnByWriteUiAction()

    data object ContinueLearnWrongAnswer : LearnByWriteUiAction()
    data object RestartLearn : LearnByWriteUiAction()
    data object OnBackClicked : LearnByWriteUiAction()
}