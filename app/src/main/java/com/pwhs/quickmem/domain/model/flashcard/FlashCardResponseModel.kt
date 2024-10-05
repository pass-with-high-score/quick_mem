package com.pwhs.quickmem.domain.model.flashcard

import com.pwhs.quickmem.core.data.Rating
data class FlashCardResponseModel(
    val id: String,
    val question: String,
    val answer: String,
    val answerImageURL: String?,
    val hint: String?,
    val explanation: String?,
    val studySetId: String,
    val rating: String = Rating.UNRATED.rating,
    val createdAt: String,
    val updatedAt: String
)
