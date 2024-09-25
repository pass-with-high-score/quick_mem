package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.SignupResponseDto
import com.pwhs.quickmem.domain.model.auth.SignupResponseModel

fun SignupResponseModel.toDto() = SignupResponseDto(
    message = message,
    isVerified = isVerified,
    success = success,
)

fun SignupResponseDto.toModel() = SignupResponseModel(
    message = message,
    isVerified = isVerified,
    success = success,
)