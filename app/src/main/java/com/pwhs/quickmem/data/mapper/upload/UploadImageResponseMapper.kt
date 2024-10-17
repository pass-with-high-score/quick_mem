package com.pwhs.quickmem.data.mapper.upload

import com.pwhs.quickmem.data.dto.upload.UploadImageResponseDto
import com.pwhs.quickmem.domain.model.upload.UploadImageResponseModel

fun UploadImageResponseDto.toUploadImageResponseModel() = UploadImageResponseModel(
    message = message,
    url = url
)

fun UploadImageResponseModel.toUploadImageResponseDto() = UploadImageResponseDto(
    message = message,
    url = url
)