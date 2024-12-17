package com.pwhs.quickmem.presentation

import com.pwhs.quickmem.domain.model.streak.StreakModel
import java.time.LocalDate

data class StandardUiState(
    val streakCount: Int = 0,
    val streaks: List<StreakModel> = emptyList(),
    val streakDates: List<LocalDate> = emptyList(),
    val isLoading: Boolean = false,
)