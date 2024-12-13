package com.pwhs.quickmem.data.dto.study_set

import com.google.gson.annotations.SerializedName

data class CreateWriteHintAIRequestDto(
    @SerializedName("flashcardId")
    val flashcardId: String,
    @SerializedName("studySetTitle")
    val studySetTitle: String,
    @SerializedName("studySetDescription")
    val studySetDescription: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("answer")
    val answer: String,
)
