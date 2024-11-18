package com.pwhs.quickmem.presentation.app.search

sealed class SearchUiAction {
    data object Search : SearchUiAction()
    data class OnQueryChanged(val query: String) : SearchUiAction()
    data class DeleteSearch(val query: String) : SearchUiAction()
    data object DeleteAllSearch : SearchUiAction()
    data class SearchWithQueryResent(val query: String) : SearchUiAction()
    data object OnRefresh: SearchUiAction()
}