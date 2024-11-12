package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.UpdateUsernameResponseDto
import com.pwhs.quickmem.domain.model.auth.UpdateUsernameResponseModel

fun UpdateUsernameResponseDto.toModel() = UpdateUsernameResponseModel(
    message = message,
    username = username
)

fun UpdateUsernameResponseModel.toDto() = UpdateUsernameResponseDto(
    message = message,
    username = username
)