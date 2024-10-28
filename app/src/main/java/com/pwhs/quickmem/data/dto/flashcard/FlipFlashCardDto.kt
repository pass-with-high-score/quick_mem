package com.pwhs.quickmem.data.dto.flashcard

import com.google.gson.annotations.SerializedName

data class FlipFlashCardDto(
    @SerializedName("flipStatus")
    val flipStatus: String
)