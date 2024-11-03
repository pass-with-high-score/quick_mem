package com.pwhs.quickmem.presentation.app.search

sealed class SearchUiAction {
    data class Search(val query: String) : SearchUiAction()
    data class OnQueryChanged(val query: String) : SearchUiAction()
}