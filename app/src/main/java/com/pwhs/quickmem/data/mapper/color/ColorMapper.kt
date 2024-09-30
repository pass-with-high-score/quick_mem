package com.pwhs.quickmem.data.mapper.color

import com.pwhs.quickmem.data.dto.color.ColorResponseDto
import com.pwhs.quickmem.domain.model.color.ColorModel

fun ColorResponseDto.toColorModel() = ColorModel(
    id = id,
    name = name,
    hexValue = hexValue
)

fun ColorModel.toColorResponseDto() = ColorResponseDto(
    id = id,
    name = name,
    hexValue = hexValue
)