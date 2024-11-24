package com.pwhs.quickmem.data.dto.flashcard

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.core.data.enums.FlipCardStatus
import com.pwhs.quickmem.core.data.enums.QuizStatus
import com.pwhs.quickmem.core.data.enums.Rating
import com.pwhs.quickmem.core.data.enums.TrueFalseStatus

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
    @SerializedName("quizStatus")
    val quizStatus: String? = QuizStatus.NONE.name,
    @SerializedName("trueFalseStatus")
    val trueFalseStatus: TrueFalseStatus? = TrueFalseStatus.NONE
)
