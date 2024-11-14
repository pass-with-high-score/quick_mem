package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.UpdateAvatarResponseDto
import com.pwhs.quickmem.domain.model.auth.UpdateAvatarResponseModel

fun UpdateAvatarResponseDto.toModel() = UpdateAvatarResponseModel(
    message = message,
    avatarUrl = avatarUrl
)

fun UpdateAvatarResponseModel.toDto() = UpdateAvatarResponseDto(
    message = message,
    avatarUrl = avatarUrl
)