package com.pwhs.quickmem.data.dto.flashcard

import com.google.gson.annotations.SerializedName

data class QuizStatusFlashCardDto(
    @SerializedName("quizStatus")
    val quizStatus: String
)