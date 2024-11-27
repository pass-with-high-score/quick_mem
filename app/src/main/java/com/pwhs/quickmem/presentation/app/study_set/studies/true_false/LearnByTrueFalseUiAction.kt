package com.pwhs.quickmem.presentation.app.study_set.studies.true_false

sealed class LearnByTrueFalseUiAction {
    data object LoadNextFlashCard : LearnByTrueFalseUiAction()
    data class OnAnswer(
        val flashcardId: String,
        val isCorrect: Boolean,
    ) : LearnByTrueFalseUiAction()

    data object ContinueLearnWrongAnswer : LearnByTrueFalseUiAction()
    data object RestartLearn : LearnByTrueFalseUiAction()
    data object OnBackClicked : LearnByTrueFalseUiAction()
}