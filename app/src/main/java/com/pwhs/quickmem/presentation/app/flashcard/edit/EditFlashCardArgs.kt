package com.pwhs.quickmem.presentation.app.flashcard.edit

import kotlinx.serialization.Serializable

@Serializable
data class EditFlashCardArgs(
    val flashcardId: String,
    val term: String,
    val definition: String,
    val definitionImageUrl: String,
    val hint: String,
    val explanation: String,
    val studySetColorId: Int,
)