package com.pwhs.quickmem.domain.model.flashcard

import com.pwhs.quickmem.core.data.enums.FlipCardStatus
import com.pwhs.quickmem.core.data.enums.QuizStatus
import com.pwhs.quickmem.core.data.enums.Rating

data class UpdateFlashCardResponseModel(
    val id: String,
    val message: String,
    val isStarred: Boolean? = false,
    val rating: String? = Rating.NOT_STUDIED.name,
    val flipStatus: String? = FlipCardStatus.NONE.name,
    val quizStatus: String? = QuizStatus.NONE.name,
)
