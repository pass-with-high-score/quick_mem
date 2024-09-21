package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.VerifyEmailRequestDto
import com.pwhs.quickmem.domain.model.auth.VerifyEmailResponseModel

fun VerifyEmailRequestDto.toModel() = VerifyEmailResponseModel(
    otp = otp,
    email = email
)

fun VerifyEmailResponseModel.toDto() = VerifyEmailRequestDto(
    otp = otp,
    email = email
)