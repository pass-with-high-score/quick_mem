package com.pwhs.quickmem.data.dto.flashcard

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.core.data.Rating
import com.pwhs.quickmem.data.dto.option.OptionDto

data class FlashCardResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("questionImageURL")
    val questionImageURL: List<String>?,
    @SerializedName("answer")
    val answer: String,
    @SerializedName("answerImageURL")
    val answerImageURL: List<String>?,
    @SerializedName("hint")
    val hint: String?,
    @SerializedName("explanation")
    val explanation: String?,
    @SerializedName("option")
    val option: List<OptionDto>?,
    @SerializedName("studySetId")
    val studySetId: String,
    @SerializedName("rating")
    val rating: String = Rating.UNRATED.rating,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)