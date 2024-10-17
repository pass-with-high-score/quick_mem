package com.pwhs.quickmem.data.dto.flashcard

import com.google.gson.annotations.SerializedName
data class CreateFlashCardDto(
    @SerializedName("definition")
    val definition: String,
    @SerializedName("definitionImageUrl")
    val definitionImageURL: String? = null,
    @SerializedName("explanation")
    val explanation: String? = null,
    @SerializedName("hint")
    val hint: String? = null,
    @SerializedName("studySetId")
    val studySetId: String,
    @SerializedName("term")
    val term: String
)