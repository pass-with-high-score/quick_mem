package com.pwhs.quickmem.presentation.app.study_set.edit

sealed class EditStudySetUiEvent {
    data object None : EditStudySetUiEvent()
    data class StudySetEdited(val id: String) : EditStudySetUiEvent()
    data object ShowLoading : EditStudySetUiEvent()
    data class ShowError(val message: String) : EditStudySetUiEvent()
}