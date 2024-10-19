package com.pwhs.quickmem.presentation.app.flashcard.edit

data class EditFlashCardArgs (
    val flashcardId: String,
    val term: String,
    val definition: String,
    val definitionImageUrl: String,
    val hint: String,
    val explanation: String
)