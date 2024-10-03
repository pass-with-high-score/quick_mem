package com.pwhs.quickmem.domain.model.option

data class OptionModel(
    val answerText: String,
    val isCorrect: Boolean,
    val imageURL: List<String>?
)
