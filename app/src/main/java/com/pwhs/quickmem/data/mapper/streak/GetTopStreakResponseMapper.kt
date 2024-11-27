package com.pwhs.quickmem.data.mapper.streak

import com.pwhs.quickmem.data.dto.streak.GetTopStreakResponseDto
import com.pwhs.quickmem.domain.model.streak.GetTopStreakResponseModel

fun GetTopStreakResponseDto.toModel() = GetTopStreakResponseModel(
    userId = userId,
    username = username,
    avatarUrl = avatarUrl,
    streakCount = streakCount,
    role = role
)

fun GetTopStreakResponseModel.toDto() = GetTopStreakResponseDto(
    userId = userId,
    username = username,
    avatarUrl = avatarUrl,
    streakCount = streakCount,
    role = role
)