package com.pwhs.quickmem.presentation.app.study_set.create_study_set

sealed class CreateStudySetUiEvent {
    data object SaveClicked : CreateStudySetUiEvent()
    data object None : CreateStudySetUiEvent()
    data object CreateStudySetSuccess : CreateStudySetUiEvent()
    data object CreateStudySetFailure : CreateStudySetUiEvent()
}