package com.pwhs.quickmem.presentation.app.flashcard.create

data class CreateFlashCardUiState(
    val studySetId: String = "",
    val studySetTitle: String = "",
    val question: String = "",
    val questionImageUrl: List<String> = emptyList(),
    val answer: String = "",
    val answerImageUrl: List<String> = emptyList(),
)