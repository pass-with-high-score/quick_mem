package com.pwhs.quickmem.data.mapper.classes

import com.pwhs.quickmem.data.dto.classes.RemoveMembersRequestDto
import com.pwhs.quickmem.domain.model.classes.RemoveMembersRequestModel

fun RemoveMembersRequestDto.toModel() = RemoveMembersRequestModel(
    userId = userId,
    classId = classId,
    memberIds = memberIds
)

fun RemoveMembersRequestModel.toDto() = RemoveMembersRequestDto(
    userId = userId,
    classId = classId,
    memberIds = memberIds
)