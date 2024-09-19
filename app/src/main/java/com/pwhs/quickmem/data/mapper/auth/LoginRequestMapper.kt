package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.LoginRequestDto
import com.pwhs.quickmem.domain.model.auth.LoginRequestModel

fun LoginRequestDto.toModel() = LoginRequestModel(
    username = username,
    password = password
)

fun LoginRequestModel.toDto() = LoginRequestDto(
    username = username,
    password = password
)
