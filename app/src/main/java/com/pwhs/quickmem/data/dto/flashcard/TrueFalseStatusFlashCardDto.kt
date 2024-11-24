package com.pwhs.quickmem.data.dto.flashcard

import com.google.gson.annotations.SerializedName

data class TrueFalseStatusFlashCardDto(
    @SerializedName("trueFalseStatus")
    val trueFalseStatus: String
)