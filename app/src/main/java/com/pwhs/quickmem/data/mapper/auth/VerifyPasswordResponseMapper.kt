package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.VerifyPasswordResponseDto
import com.pwhs.quickmem.domain.model.auth.VerifyPasswordResponseModel

fun VerifyPasswordResponseDto.toModel() = VerifyPasswordResponseModel(
    success = success,
    message = message
)

fun VerifyPasswordResponseModel.toDto() = VerifyPasswordResponseDto(
    success = success,
    message = message
)