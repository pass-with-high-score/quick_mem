package com.pwhs.quickmem.core.data.states

import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel

data class WrongAnswer(
    val flashCard: FlashCardResponseModel,
    val userAnswer: String,
)