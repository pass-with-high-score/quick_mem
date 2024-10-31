package com.pwhs.quickmem.presentation.app.folder.edit

sealed class EditFolderUiEvent {
    data class FolderEdited(val id: String) : EditFolderUiEvent()
    data class ShowError(val message: String) : EditFolderUiEvent()
}