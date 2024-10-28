package com.pwhs.quickmem.data.mapper.auth

import com.pwhs.quickmem.data.dto.auth.UpdateFullNameResponseDto
import com.pwhs.quickmem.domain.model.auth.UpdateFullNameResponseModel

fun UpdateFullNameResponseDto.toModel() = UpdateFullNameResponseModel(
    message = message,
    fullname = fullname
)

fun UpdateFullNameResponseModel.toDto() = UpdateFullNameResponseDto(
    message = message,
    fullname = fullname
)
