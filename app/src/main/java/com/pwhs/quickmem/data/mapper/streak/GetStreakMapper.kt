package com.pwhs.quickmem.data.mapper.streak

import com.pwhs.quickmem.data.dto.streak.GetStreakDto
import com.pwhs.quickmem.domain.model.streak.GetStreakModel

fun GetStreakDto.toModel() = GetStreakModel(
    userId = userId,
    streaks = streaks.map { it.toModel() }
)

fun GetStreakModel.toDto() = GetStreakDto(
    userId = userId,
    streaks = streaks.map { it.toDto() }
)