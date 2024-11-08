package com.pwhs.quickmem.presentation.app.classes.add_study_set

sealed class AddStudySetToClassUiEvent {
    data object StudySetAddedToClass : AddStudySetToClassUiEvent()
    data class Error(val message: String) : AddStudySetToClassUiEvent()
}