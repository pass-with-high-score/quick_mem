package com.pwhs.quickmem.presentation.app.explore

import com.pwhs.quickmem.domain.model.streak.GetTopStreakResponseModel

data class ExploreUiState(
    val isLoading: Boolean = false,
    val topStreaks: List<GetTopStreakResponseModel> = emptyList()
)