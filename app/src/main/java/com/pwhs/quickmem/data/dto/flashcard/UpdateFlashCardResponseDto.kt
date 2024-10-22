package com.pwhs.quickmem.data.dto.flashcard

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.core.data.FlipCardStatus
import com.pwhs.quickmem.core.data.Rating

data class UpdateFlashCardResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("isStarred")
    val isStarred: Boolean? = false,
    @SerializedName("rating")
    val rating: String? = Rating.NOT_STUDIED.name,
    @SerializedName("flipStatus")
    val flipStatus: String? = FlipCardStatus.NONE.name,
)
