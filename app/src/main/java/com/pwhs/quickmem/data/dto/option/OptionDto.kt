package com.pwhs.quickmem.data.dto.option

import com.google.gson.annotations.SerializedName

data class OptionDto(
    @SerializedName("answerText")
    val answerText: String,
    @SerializedName("isCorrect")
    val isCorrect: Boolean,
    @SerializedName("imageURL")
    val imageURL: List<String>?
)
