package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.UpdateEmailResponseDto
import com.pwhs.quickmem.domain.model.auth.UpdateEmailResponseModel

fun UpdateEmailResponseDto.toModel() = UpdateEmailResponseModel(
    message = message,
    email = email
)

fun UpdateEmailResponseModel.toDto() = UpdateEmailResponseDto(
    message = message,
    email = email
)
