package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.OtpResponseDto
import com.pwhs.quickmem.domain.model.auth.OtpResponseModel

fun OtpResponseDto.toModel() = OtpResponseModel(
    otp = otp,
    email = email
)