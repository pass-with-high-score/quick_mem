package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.ResendEmailRequestDto
import com.pwhs.quickmem.domain.model.auth.ResendEmailRequestModel

fun ResendEmailRequestDto.toModel() = ResendEmailRequestModel(
    email = email
)

fun ResendEmailRequestModel.toDto() = ResendEmailRequestDto(
    email = email!!
)