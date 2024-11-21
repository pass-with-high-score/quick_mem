package com.pwhs.quickmem.presentation.app.study_set.studies.true_false

import com.pwhs.quickmem.core.data.enums.QuizStatus

sealed class LearnByTrueFalseUiAction {
    data object LoadNextFlashCard : LearnByTrueFalseUiAction()
    data class SubmitCorrectAnswer(
        val flashCardId: String,
        val quizStatus: QuizStatus,
        val userAnswer: String
    ) : LearnByTrueFalseUiAction()

    data object ContinueLearnWrongAnswer : LearnByTrueFalseUiAction()
    data object RestartLearn : LearnByTrueFalseUiAction()
}