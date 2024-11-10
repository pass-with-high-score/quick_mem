package com.pwhs.quickmem.presentation.app.study_set.add_to_folder

sealed class AddStudySetToFoldersUiEvent {
    data object StudySetAddedToFolders : AddStudySetToFoldersUiEvent()
    data class Error(val message: String) : AddStudySetToFoldersUiEvent()
}