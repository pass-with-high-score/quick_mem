package com.pwhs.quickmem.presentation.app.study_set.edit

sealed class EditStudySetUiEvent {
    data class StudySetEdited(val id: String) : EditStudySetUiEvent()
    data class ShowError(val message: String) : EditStudySetUiEvent()
}