package com.pwhs.quickmem.data.mapper

import com.pwhs.quickmem.core.data.UserRole
import com.pwhs.quickmem.data.dto.UserDto
import com.pwhs.quickmem.domain.model.UserModel

fun UserDto.toUserModel() = UserModel(
    id = id,
    fullName = fullName ?: "",
    avatarUrl = avatarUrl ?: "",
    email = email ?: "",
    userName = userName ?: "",
    role = role ?: UserRole.STUDENT,
    birthDay = birthDay ?: ""
)

fun UserModel.toUserDto() = UserDto(
    id = id,
    fullName = fullName,
    avatarUrl = avatarUrl,
    email = email,
    userName = userName,
    role = role,
    birthDay = birthDay
)