package com.pwhs.quickmem.presentation.app.search

import com.pwhs.quickmem.domain.model.search.SearchQueryModel

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val listResult: List<SearchQueryModel> = emptyList()
)

