package com.pwhs.quickmem.presentation.app.search

sealed class SearchUiAction {
    data object Search : SearchUiAction()
    data class OnQueryChanged(val query: String) : SearchUiAction()
}