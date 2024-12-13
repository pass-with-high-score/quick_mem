package com.pwhs.quickmem.domain.model.study_set

data class CreateWriteHintAIRequestModel(
    val flashcardId: String,
    val studySetTitle: String,
    val studySetDescription: String,
    val question: String,
    val answer: String,
)
