package com.pwhs.quickmem.presentation.app.search_result

sealed class SearchResultUiEvent(){
    data class Error(val message: String) : SearchResultUiEvent()
}
