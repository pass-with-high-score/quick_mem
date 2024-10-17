package com.pwhs.quickmem.data.dto.flashcard

import com.google.gson.annotations.SerializedName
data class CreateFlashCardDto(
    @SerializedName("definition")
    val definition: String,
    @SerializedName("definitionImageUrl")
    val definitionImageURL: String,
    @SerializedName("explanation")
    val explanation: String,
    @SerializedName("hint")
    val hint: String,
    @SerializedName("studySetId")
    val studySetId: String,
    @SerializedName("term")
    val term: String
)