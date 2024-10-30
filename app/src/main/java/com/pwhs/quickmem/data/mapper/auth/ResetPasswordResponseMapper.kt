package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.ResetPasswordResponseDto
import com.pwhs.quickmem.domain.model.auth.ResetPasswordResponseModel

fun ResetPasswordResponseDto.toModel() = ResetPasswordResponseModel(
    email = email,
    isReset = isReset,
    message = message
)

fun ResetPasswordResponseModel.toDto() = ResetPasswordResponseDto(
    email = email,
    isReset = isReset,
    message = message
)