package com.pwhs.quickmem.presentation.app.study_set.add_to_class

sealed class AddStudySetToClassesUiEvent {
    data object StudySetAddedToClasses : AddStudySetToClassesUiEvent()
    data class Error(val message: String) : AddStudySetToClassesUiEvent()
}