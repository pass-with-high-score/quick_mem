package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.SignupRequestDto
import com.pwhs.quickmem.domain.model.auth.SignupRequestModel

fun SignupRequestDto.toModel() = SignupRequestModel(
    avatarUrl = avatarUrl,
    email = email,
    username = username,
    fullName = fullName,
    role = role,
    birthday = birthday,
    password = password
)

fun SignupRequestModel.toDto() = SignupRequestDto(
    avatarUrl = avatarUrl,
    email = email,
    username = username,
    fullName = fullName,
    role = role,
    birthday = birthday,
    password = password
)