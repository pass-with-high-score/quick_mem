package com.pwhs.quickmem.presentation.app.flashcard.create

import android.net.Uri

data class CreateFlashCardUiState(
    val studySetId: String = "",
    val studySetTitle: String = "",
    val question: String = "",
    val answer: String = "",
    val answerImage: Uri? = null,
)
