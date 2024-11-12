package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.UpdateUsernameRequestDto
import com.pwhs.quickmem.domain.model.auth.UpdateUsernameRequestModel

fun UpdateUsernameRequestDto.toModel() = UpdateUsernameRequestModel(
    userId = userId,
    username = username
)

fun UpdateUsernameRequestModel.toDto() = UpdateUsernameRequestDto(
    userId = userId,
    username = username
)