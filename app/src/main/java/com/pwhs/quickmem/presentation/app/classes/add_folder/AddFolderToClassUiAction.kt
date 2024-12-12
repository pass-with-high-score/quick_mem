package com.pwhs.quickmem.presentation.app.classes.add_folder

sealed class AddFolderToClassUiAction {
    data object AddFolderToClass : AddFolderToClassUiAction()
    data class ToggleFolderImport(val folderId: String) : AddFolderToClassUiAction()
    data object RefreshFolders : AddFolderToClassUiAction()
}