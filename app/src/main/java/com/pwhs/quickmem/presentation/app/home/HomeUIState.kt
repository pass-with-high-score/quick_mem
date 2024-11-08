package com.pwhs.quickmem.presentation.app.home

data class HomeUIState(
    val isLoading: Boolean = false,
    val userId: String = "",
    val streakCount: Int = 0,
)