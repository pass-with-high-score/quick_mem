package com.pwhs.quickmem.presentation.app.study_set.studies.quiz

import com.pwhs.quickmem.core.data.enums.QuizStatus

sealed class LearnByQuizUiAction {
    data object LoadNextFlashCard : LearnByQuizUiAction()
    data class SubmitCorrectAnswer(
        val flashCardId: String,
        val quizStatus: QuizStatus,
        val userAnswer: String
    ) : LearnByQuizUiAction()
    data object ContinueLearnWrongAnswer : LearnByQuizUiAction()
    data object RestartLearn : LearnByQuizUiAction()
    data object OnBackClicked : LearnByQuizUiAction()
}