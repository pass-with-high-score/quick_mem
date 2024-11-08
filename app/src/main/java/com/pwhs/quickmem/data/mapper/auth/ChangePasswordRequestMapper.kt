package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.ChangePasswordRequestDto
import com.pwhs.quickmem.domain.model.auth.ChangePasswordRequestModel

fun ChangePasswordRequestDto.toModel() = ChangePasswordRequestModel(
    email = email,
    oldPassword = oldPassword,
    newPassword = newPassword
)

fun ChangePasswordRequestModel.toDto() = ChangePasswordRequestDto(
    email = email,
    oldPassword = oldPassword,
    newPassword = newPassword
)