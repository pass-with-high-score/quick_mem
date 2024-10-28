package com.pwhs.quickmem.presentation.app.folder.create

sealed class CreateFolderUiEvent {
    data object None : CreateFolderUiEvent()
    data class FolderCreated(val id: String) : CreateFolderUiEvent()
    data object ShowLoading : CreateFolderUiEvent()
    data class ShowError(val message: String) : CreateFolderUiEvent()
}