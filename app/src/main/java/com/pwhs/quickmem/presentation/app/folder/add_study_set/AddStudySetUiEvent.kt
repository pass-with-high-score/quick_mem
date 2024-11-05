package com.pwhs.quickmem.presentation.app.folder.add_study_set

sealed class AddStudySetUiEvent {
    data class Error(val message: String) : AddStudySetUiEvent()
}