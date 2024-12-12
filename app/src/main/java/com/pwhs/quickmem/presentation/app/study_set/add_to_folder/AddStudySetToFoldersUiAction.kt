package com.pwhs.quickmem.presentation.app.study_set.add_to_folder

sealed class AddStudySetToFoldersUiAction {
    data object AddStudySetToFolders : AddStudySetToFoldersUiAction()
    data object RefreshFolders : AddStudySetToFoldersUiAction()
    data class ToggleStudySetImport(val folderId: String) : AddStudySetToFoldersUiAction()
}