package com.pwhs.quickmem.data.mapper.pixabay

import com.pwhs.quickmem.data.dto.pixabay.PixaBayImageDto
import com.pwhs.quickmem.domain.model.pixabay.PixaBayImageModel

fun PixaBayImageDto.toModel() = PixaBayImageModel(
    id = id,
    imageUrl = imageUrl,
)

fun PixaBayImageModel.toDto() = PixaBayImageDto(
    id = id,
    imageUrl = imageUrl,
)