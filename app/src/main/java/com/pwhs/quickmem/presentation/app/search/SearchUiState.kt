package com.pwhs.quickmem.presentation.app.search

import com.pwhs.quickmem.domain.model.search.SearchQueryModel

data class SearchUiState(
    val query: String = "",
    val error: String = "",
    val listResult: List<SearchQueryModel> = emptyList()
)

