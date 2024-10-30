package com.pwhs.quickmem.presentation.app.study_set.create

sealed class CreateStudySetUiEvent {
    data class StudySetCreated(val id: String) : CreateStudySetUiEvent()
    data class ShowError(val message: String) : CreateStudySetUiEvent()
}