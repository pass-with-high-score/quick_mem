package com.pwhs.quickmem.presentation.app.folder.create

sealed class CreateFolderUiAction {
    data class TitleChanged(val title: String) : CreateFolderUiAction()
    data class DescriptionChanged(val description: String) : CreateFolderUiAction()
    data class PublicChanged(val isPublic: Boolean) : CreateFolderUiAction()
    data object SaveClicked : CreateFolderUiAction()
}