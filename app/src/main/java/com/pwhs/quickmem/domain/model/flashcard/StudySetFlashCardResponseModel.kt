package com.pwhs.quickmem.domain.model.flashcard

import com.pwhs.quickmem.core.data.Rating

data class StudySetFlashCardResponseModel(
    val id: String,
    val term: String,
    val definition: String,
    val definitionImageURL: String?,
    val hint: String?,
    val explanation: String?,
    val rating: String = Rating.NOT_STUDIED.name,
    val isStarred: Boolean,
    val createdAt: String,
    val updatedAt: String
)
