package com.pwhs.quickmem.data.mapper.upload

import com.pwhs.quickmem.data.dto.upload.DeleteImageDto
import com.pwhs.quickmem.domain.model.upload.DeleteImageModel

fun DeleteImageModel.toDto() = DeleteImageDto(
    imageURL = imageURL
)

fun DeleteImageDto.toModel() = DeleteImageModel(
    imageURL = imageURL
)