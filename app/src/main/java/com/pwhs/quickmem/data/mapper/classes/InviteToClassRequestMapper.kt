package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.InviteToClassRequestDto
import com.pwhs.quickmem.domain.model.classes.InviteToClassRequestModel

fun InviteToClassRequestModel.toDto() = InviteToClassRequestDto(
    classId = classId,
    username = username
)

fun InviteToClassRequestDto.toModel() = InviteToClassRequestModel(
    classId = classId,
    username = username
)