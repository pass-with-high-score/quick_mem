package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.AuthResponseDto
import com.pwhs.quickmem.domain.model.auth.AuthResponseModel

fun AuthResponseDto.toModel() = AuthResponseModel(
    id = id,
    fullName = fullName,
    email = email,
    username = username,
    role = role,
    avatarUrl = avatarUrl,
    birthday = birthday,
)