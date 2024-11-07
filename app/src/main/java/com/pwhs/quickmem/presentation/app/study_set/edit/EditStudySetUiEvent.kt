package com.pwhs.quickmem.presentation.app.study_set.edit

sealed class EditStudySetUiEvent {
    data object StudySetEdited : EditStudySetUiEvent()
    data class ShowError(val message: String) : EditStudySetUiEvent()
}