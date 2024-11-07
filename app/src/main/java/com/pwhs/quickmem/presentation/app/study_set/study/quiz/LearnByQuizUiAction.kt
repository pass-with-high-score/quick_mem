package com.pwhs.quickmem.presentation.app.study_set.study.quiz

sealed class LearnByQuizUiAction {
    data object LoadNextFlashCard : LearnByQuizUiAction()
    data class SubmitCorrectAnswer(val flashCardId: String, val isCorrect: Boolean) :
        LearnByQuizUiAction()
}