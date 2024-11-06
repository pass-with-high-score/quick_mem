package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.UpdateEmailRequestDto
import com.pwhs.quickmem.domain.model.auth.UpdateEmailRequestModel

fun UpdateEmailRequestDto.toModel() = UpdateEmailRequestModel(
    userId = userId,
    email = email
)

fun UpdateEmailRequestModel.toDto() = UpdateEmailRequestDto(
    userId = userId,
    email = email
)

