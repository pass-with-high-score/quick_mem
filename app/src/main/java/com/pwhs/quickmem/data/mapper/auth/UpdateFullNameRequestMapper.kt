package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.UpdateFullNameRequestDto
import com.pwhs.quickmem.domain.model.auth.UpdateFullNameRequestModel

fun UpdateFullNameRequestDto.toModel() = UpdateFullNameRequestModel(
    userId = userId,
    fullname = fullName
)

fun UpdateFullNameRequestModel.toDto() = UpdateFullNameRequestDto(
    userId = userId,
    fullName = fullname
)