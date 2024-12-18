package com.pwhs.quickmem.data.mapper.user

import com.pwhs.quickmem.data.dto.user.AvatarResponseDto
import com.pwhs.quickmem.domain.model.users.AvatarResponseModel

fun AvatarResponseDto.toModel() = AvatarResponseModel(
    id = id,
    url = url
)