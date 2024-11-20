package com.pwhs.quickmem.core.data.states

data class RandomAnswer(
    val answer: String = "",
    val isCorrect: Boolean = false,
    val imageURL: String = "",
)
