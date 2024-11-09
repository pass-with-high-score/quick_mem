package com.pwhs.quickmem.presentation.app.classes.add_folder

sealed class AddFolderToClassUIAction {
    data object AddFolderToClass : AddFolderToClassUIAction()
    data class ToggleFolderImport(val folderId: String) : AddFolderToClassUIAction()
}