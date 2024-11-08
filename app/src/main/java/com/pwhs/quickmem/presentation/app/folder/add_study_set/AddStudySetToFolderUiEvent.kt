package com.pwhs.quickmem.presentation.app.folder.add_study_set

sealed class AddStudySetToFolderUiEvent {
    data object StudySetAddedToFolder : AddStudySetToFolderUiEvent()
    data class Error(val message: String) : AddStudySetToFolderUiEvent()
}