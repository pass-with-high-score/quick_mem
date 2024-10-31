package com.pwhs.quickmem.presentation.app.folder.edit

sealed class EditFolderUiAction {
    data class TitleChanged(val title: String) : EditFolderUiAction()
    data class DescriptionChanged(val description: String) : EditFolderUiAction()
    data class IsPublicChanged(val isPublic: Boolean) : EditFolderUiAction()
    data object SaveClicked : EditFolderUiAction()
}