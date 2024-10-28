package com.pwhs.quickmem.presentation.app.study_set.study.quiz

sealed class LearnFlashCardUiAction {
    data object LoadNextFlashCard : LearnFlashCardUiAction()
    data class SubmitCorrectAnswer(val flashCardId: String, val isCorrect: Boolean) :
        LearnFlashCardUiAction()
}