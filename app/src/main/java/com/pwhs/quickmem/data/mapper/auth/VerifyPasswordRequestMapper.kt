package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.VerifyPasswordRequestDto
import com.pwhs.quickmem.domain.model.auth.VerifyPasswordRequestModel

fun VerifyPasswordRequestDto.toModel() = VerifyPasswordRequestModel(
    userId = userId,
    password = password
)

fun VerifyPasswordRequestModel.toDto() = VerifyPasswordRequestDto(
    userId = userId,
    password = password
)