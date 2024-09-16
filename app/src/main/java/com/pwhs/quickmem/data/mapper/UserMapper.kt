package com.pwhs.quickmem.data.mapper

import com.pwhs.quickmem.core.data.UserRole
import com.pwhs.quickmem.data.dto.SignupDto
import com.pwhs.quickmem.domain.model.UserModel

fun SignupDto.toUserModel() = UserModel(
    id = id,
    fullName = fullName ?: "",
    avatarUrl = avatarUrl ?: "",
    email = email ?: "",
    userName = userName ?: "",
    role = role ?: UserRole.STUDENT,
    birthDay = birthDay ?: "",
    createdAt = createdAt ?: "",
    updatedAt = updatedAt ?: ""
)

fun UserModel.toUserDto() = SignupDto(
    id = id,
    fullName = fullName,
    avatarUrl = avatarUrl,
    email = email,
    userName = userName,
    role = role,
    birthDay = birthDay,
    createdAt = createdAt,
    updatedAt = updatedAt
)