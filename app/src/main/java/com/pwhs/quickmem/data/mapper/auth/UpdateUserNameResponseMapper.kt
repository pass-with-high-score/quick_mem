package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.UpdateUsernameResponseDto
import com.pwhs.quickmem.domain.model.auth.UpdateUsernameResponseModel

fun UpdateUsernameResponseDto.toModel() = UpdateUsernameResponseModel(
    message = message,
    newUsername = newUsername
)

fun UpdateUsernameResponseModel.toDto() = UpdateUsernameResponseDto(
    message = message,
    newUsername = newUsername
)