package com.pwhs.quickmem.domain.model.flashcard

import com.pwhs.quickmem.core.data.enums.FlipCardStatus
import com.pwhs.quickmem.core.data.enums.Rating

data class StudySetFlashCardResponseModel(
    val id: String,
    val term: String,
    val definition: String,
    val definitionImageURL: String? = null,
    val hint: String? = null,
    val explanation: String? = null,
    val rating: String = Rating.NOT_STUDIED.name,
    val flipStatus: String = FlipCardStatus.NONE.name,
    val isStarred: Boolean = false,
    val createdAt: String = "",
    val updatedAt: String = ""
)
