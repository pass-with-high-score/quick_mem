package com.pwhs.quickmem.data.dto.study_set

import com.google.gson.annotations.SerializedName

data class CreateWriteHintAIResponseDto(
    @SerializedName("flashcardId")
    val flashcardId: String,
    @SerializedName("aiHint")
    val aiHint: String,
)
