package com.pwhs.quickmem.data.mapper.user

import com.pwhs.quickmem.data.dto.user.ClassMemberDto
import com.pwhs.quickmem.domain.model.users.ClassMemberModel

fun ClassMemberDto.toModel() = ClassMemberModel(
    id = id,
    username = username,
    avatarUrl = avatarUrl,
    isOwner = isOwner,
    role = role,
)

fun ClassMemberModel.toDto() = ClassMemberDto(
    id = id,
    username = username,
    avatarUrl = avatarUrl,
    isOwner = isOwner,
    role = role,
)