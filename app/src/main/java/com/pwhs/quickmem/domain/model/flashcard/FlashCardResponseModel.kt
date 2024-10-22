package com.pwhs.quickmem.domain.model.flashcard

import com.pwhs.quickmem.core.data.FlipCardStatus
import com.pwhs.quickmem.core.data.Rating

data class FlashCardResponseModel(
    val id: String,
    val term: String,
    val definition: String,
    val definitionImageURL: String?,
    val hint: String?,
    val explanation: String?,
    val studySetId: String,
    val rating: String = Rating.NOT_STUDIED.name,
    val flipStatus: String = FlipCardStatus.NONE.name,
    val isStarred: Boolean,
    val createdAt: String,
    val updatedAt: String
)
