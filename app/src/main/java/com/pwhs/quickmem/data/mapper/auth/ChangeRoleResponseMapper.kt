package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.ChangeRoleResponseDto
import com.pwhs.quickmem.domain.model.auth.ChangeRoleResponseModel

fun ChangeRoleResponseDto.toModel() = ChangeRoleResponseModel(
    message = message,
    role = role,
    success = success
)

fun ChangeRoleResponseModel.toDto() = ChangeRoleResponseDto(
    message = message,
    role = role,
    success = success
)