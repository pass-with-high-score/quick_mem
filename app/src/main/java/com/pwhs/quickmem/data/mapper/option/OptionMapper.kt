package com.pwhs.quickmem.data.mapper.option

import com.pwhs.quickmem.data.dto.option.OptionDto
import com.pwhs.quickmem.domain.model.option.OptionModel

fun OptionDto.toModel() = OptionModel(
    answerText = answerText,
    isCorrect = isCorrect,
    imageURL = imageURL
)

fun OptionModel.toDto() = OptionDto(
    answerText = answerText,
    isCorrect = isCorrect,
    imageURL = imageURL
)