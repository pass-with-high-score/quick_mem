package com.pwhs.quickmem.presentation.app.folder.add_study_set

sealed class AddStudySetUiAction {
    data object AddStudySet : AddStudySetUiAction()
    data class ToggleStudySetImport(val studySetId: String) : AddStudySetUiAction()
}