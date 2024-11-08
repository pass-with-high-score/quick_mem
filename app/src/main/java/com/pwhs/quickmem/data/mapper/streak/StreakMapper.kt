package com.pwhs.quickmem.data.mapper.streak

import com.pwhs.quickmem.data.dto.streak.StreakDto
import com.pwhs.quickmem.domain.model.streak.StreakModel

fun StreakDto.toModel() = StreakModel(
    id = id,
    streakCount = streakCount,
    date = date,
)

fun StreakModel.toDto() = StreakDto(
    id = id,
    streakCount = streakCount,
    date = date,
)