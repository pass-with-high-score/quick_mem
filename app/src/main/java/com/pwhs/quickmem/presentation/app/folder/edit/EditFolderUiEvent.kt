package com.pwhs.quickmem.presentation.app.folder.edit

sealed class EditFolderUiEvent {
    data object FolderEdited : EditFolderUiEvent()
    data class ShowError(val message: String) : EditFolderUiEvent()
}