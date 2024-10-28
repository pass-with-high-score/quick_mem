package com.pwhs.quickmem.data.dto.flashcard

import com.google.gson.annotations.SerializedName

data class RatingFlashCardDto(
    @SerializedName("rating")
    val rating: String
)