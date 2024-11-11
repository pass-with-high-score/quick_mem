package com.pwhs.quickmem.presentation.app.study_set.add_to_class

sealed class AddStudySetToClassesUiAction {
    data object AddStudySetToClasses : AddStudySetToClassesUiAction()
    data class ToggleStudySetImport(val classId: String) : AddStudySetToClassesUiAction()
}