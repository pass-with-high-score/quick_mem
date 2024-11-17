package com.pwhs.quickmem.presentation.app.search

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val listResult: List<String> = emptyList()
)

