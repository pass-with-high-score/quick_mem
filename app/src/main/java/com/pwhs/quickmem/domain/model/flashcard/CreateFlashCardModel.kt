package com.pwhs.quickmem.domain.model.flashcard

data class CreateFlashCardModel(
    val definition: String,
    val definitionImageURL: String,
    val explanation: String,
    val hint: String,
    val studySetId: String,
    val term: String
)