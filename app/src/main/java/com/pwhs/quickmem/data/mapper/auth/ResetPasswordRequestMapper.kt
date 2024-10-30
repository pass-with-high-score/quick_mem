package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.ResetPasswordRequestDto
import com.pwhs.quickmem.domain.model.auth.ResetPasswordRequestModel

fun ResetPasswordRequestDto.toModel() = ResetPasswordRequestModel(
    email = email,
    newPassword = newPassword,
    otp = otp,
    resetPasswordToken = resetPasswordToken
)

fun ResetPasswordRequestModel.toDto() = ResetPasswordRequestDto(
    email = email,
    newPassword = newPassword,
    otp = otp,
    resetPasswordToken = resetPasswordToken
)