package com.pwhs.quickmem.presentation.app.folder.detail

sealed class FolderDetailUiEvent {
    data object FolderDeleted : FolderDetailUiEvent()
    data object NavigateToEditFolder : FolderDetailUiEvent()
    data class ShowError(val message: String) : FolderDetailUiEvent()
    data class OnNavigateToFlipFlashcard(val isGetAll: Boolean) : FolderDetailUiEvent()
    data class OnNavigateToQuiz(val isGetAll: Boolean) : FolderDetailUiEvent()
    data class OnNavigateToTrueFalse(val isGetAll: Boolean) : FolderDetailUiEvent()
    data class OnNavigateToWrite(val isGetAll: Boolean) : FolderDetailUiEvent()
}