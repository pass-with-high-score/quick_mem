package com.pwhs.quickmem.presentation.app.classes.add_folder

sealed class AddFolderToClassUIAction {
    data object AddStudySetToClass : AddFolderToClassUIAction()
    data class ToggleStudySetImport(val folderId: String) : AddFolderToClassUIAction()
}