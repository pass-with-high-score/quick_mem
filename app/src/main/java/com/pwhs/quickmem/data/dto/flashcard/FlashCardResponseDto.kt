package com.pwhs.quickmem.data.dto.flashcard

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.core.data.Rating

data class FlashCardResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("answer")
    val answer: String,
    @SerializedName("answerImageURL")
    val answerImageURL: String?,
    @SerializedName("hint")
    val hint: String?,
    @SerializedName("explanation")
    val explanation: String?,
    @SerializedName("studySetId")
    val studySetId: String,
    @SerializedName("rating")
    val rating: String = Rating.UNRATED.rating,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)