package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.AddMemberToClassRequestDto
import com.pwhs.quickmem.domain.model.classes.AddMemberToClassRequestModel

fun AddMemberToClassRequestDto.toModel() = AddMemberToClassRequestModel(
    joinToken = joinToken,
    userId = userId,
    classId = classId
)

fun AddMemberToClassRequestModel.toDto() = AddMemberToClassRequestDto(
    joinToken = joinToken,
    userId = userId,
    classId = classId
)