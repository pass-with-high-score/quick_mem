package com.pwhs.quickmem.data.mapper.user

import com.pwhs.quickmem.data.dto.user.UpdateCoinResponseDto
import com.pwhs.quickmem.domain.model.users.UpdateCoinResponseModel

fun UpdateCoinResponseDto.toModel() = UpdateCoinResponseModel(
    message = message,
    coinAction = coinAction,
    coins = coins
)

fun UpdateCoinResponseModel.toDto() = UpdateCoinResponseDto(
    message = message,
    coinAction = coinAction,
    coins = coins
)