package com.pwhs.quickmem.presentation.app.home

data class HomeUIState(
    val isLoading: Boolean = false,
    val hasData: Boolean = false,
    val error: String = ""
)