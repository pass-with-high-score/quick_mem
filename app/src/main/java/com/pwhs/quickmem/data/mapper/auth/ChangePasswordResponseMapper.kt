package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.ChangePasswordResponseDto
import com.pwhs.quickmem.domain.model.auth.ChangePasswordResponseModel

fun ChangePasswordResponseDto.toModel() = ChangePasswordResponseModel(
    isSet = isSet,
    message = message,
    email = email
)

fun ChangePasswordResponseModel.toDto() = ChangePasswordResponseDto(
    isSet = isSet,
    message = message,
    email = email
)