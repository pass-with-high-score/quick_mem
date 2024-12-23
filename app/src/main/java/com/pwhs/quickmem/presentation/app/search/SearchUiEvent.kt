package com.pwhs.quickmem.presentation.app.search

sealed class SearchUiEvent {
    data class NavigateToResult(val query: String) : SearchUiEvent()
    data class ShowError(val error: String) : SearchUiEvent()
    data object ClearAllSearchRecent : SearchUiEvent()
}