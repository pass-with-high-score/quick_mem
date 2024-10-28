package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.SendResetPasswordRequestDto
import com.pwhs.quickmem.domain.model.auth.SendResetPasswordRequestModel

fun SendResetPasswordRequestDto.toModel() = SendResetPasswordRequestModel(
    email = email
)

fun SendResetPasswordRequestModel.toDto() = SendResetPasswordRequestDto(
    email = email
)