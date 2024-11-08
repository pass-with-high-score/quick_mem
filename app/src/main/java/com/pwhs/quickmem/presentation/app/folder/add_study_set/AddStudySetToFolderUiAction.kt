package com.pwhs.quickmem.presentation.app.folder.add_study_set

sealed class AddStudySetToFolderUiAction {
    data object AddStudySetToFolder : AddStudySetToFolderUiAction()
    data class ToggleStudySetImport(val studySetId: String) : AddStudySetToFolderUiAction()
}