package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.GetAvatarResponseDto
import com.pwhs.quickmem.domain.model.auth.GetAvatarResponseModel

fun GetAvatarResponseModel.toDto() = GetAvatarResponseDto(
    id = id,
    url = url
)

fun GetAvatarResponseDto.toModel() = GetAvatarResponseModel(
    id = id,
    url = url
)