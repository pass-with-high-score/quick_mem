package com.pwhs.quickmem.presentation.app.search_result

sealed class SearchResultUiAction() {
    data object Refresh : SearchResultUiAction()
    data object RefreshStudySets : SearchResultUiAction()
    data object RefreshClasses : SearchResultUiAction()
    data object RefreshFolders : SearchResultUiAction()
}
