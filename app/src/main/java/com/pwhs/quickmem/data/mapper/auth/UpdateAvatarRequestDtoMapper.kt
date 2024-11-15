package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.UpdateAvatarRequestDto
import com.pwhs.quickmem.domain.model.auth.UpdateAvatarRequestModel

fun UpdateAvatarRequestDto.toModel() = UpdateAvatarRequestModel(
    avatar = avatar
)

fun UpdateAvatarRequestModel.toDto() = UpdateAvatarRequestDto(
    avatar = avatar
)