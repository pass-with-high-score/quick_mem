package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.SendResetPasswordResponseDto
import com.pwhs.quickmem.domain.model.auth.SendResetPasswordResponseModel

fun SendResetPasswordResponseDto.toModel() = SendResetPasswordResponseModel(
    message = message,
    email = email,
    resetPasswordToken = resetPasswordToken,
    isSent = isSent
)

fun SendResetPasswordResponseModel.toDto() = SendResetPasswordResponseDto(
    message = message,
    email = email,
    resetPasswordToken = resetPasswordToken,
    isSent = isSent
)