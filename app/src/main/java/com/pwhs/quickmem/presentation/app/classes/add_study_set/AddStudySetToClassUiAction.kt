package com.pwhs.quickmem.presentation.app.classes.add_study_set

sealed class AddStudySetToClassUiAction {
    data object AddStudySetToClass : AddStudySetToClassUiAction()
    data class ToggleStudySetImport(val studySetId: String) : AddStudySetToClassUiAction()
}