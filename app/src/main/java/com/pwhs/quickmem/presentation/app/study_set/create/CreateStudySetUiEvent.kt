package com.pwhs.quickmem.presentation.app.study_set.create

sealed class CreateStudySetUiEvent {
    data object SaveClicked : CreateStudySetUiEvent()
    data object None : CreateStudySetUiEvent()
    data object StudySetCreated : CreateStudySetUiEvent()
    data object ShowLoading : CreateStudySetUiEvent()
    data class ShowError(val message: String) : CreateStudySetUiEvent()
}