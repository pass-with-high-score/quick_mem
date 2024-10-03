package com.pwhs.quickmem.presentation.app.flashcard.create

import android.net.Uri

data class CreateFlashCardUiState(
    val studySetId: String = "",
    val studySetTitle: String = "",
    val images: List<Uri> = emptyList(),
    val question: String = "",
    val questionImageUrl: List<String> = emptyList(),
    val answer: String = "",
    val answerImageUrl: List<String> = emptyList(),
)