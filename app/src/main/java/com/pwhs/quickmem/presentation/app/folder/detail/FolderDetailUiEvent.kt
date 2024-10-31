package com.pwhs.quickmem.presentation.app.folder.detail

sealed class FolderDetailUiEvent {
    data object StudySetDeleted : FolderDetailUiEvent()
    data object NavigateToEditFolder : FolderDetailUiEvent()
    data class ShowError(val message: String) : FolderDetailUiEvent()
}