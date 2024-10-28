package com.pwhs.quickmem.presentation.app.folder.create

sealed class CreateFolderUiEvent {
    data class FolderCreated(val id: String) : CreateFolderUiEvent()
    data class ShowError(val message: String) : CreateFolderUiEvent()
}