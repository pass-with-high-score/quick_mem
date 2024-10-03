package com.pwhs.quickmem.domain.model.flashcard

import com.pwhs.quickmem.core.data.Rating
import com.pwhs.quickmem.domain.model.option.OptionModel

data class FlashCardResponseModel(
    val id: String,
    val question: String,
    val questionImageURL: List<String>?,
    val answer: String,
    val answerImageURL: List<String>?,
    val hint: String?,
    val explanation: String?,
    val option: List<OptionModel>?,
    val studySetId: String,
    val rating: String = Rating.UNRATED.rating,
    val createdAt: String,
    val updatedAt: String
)
