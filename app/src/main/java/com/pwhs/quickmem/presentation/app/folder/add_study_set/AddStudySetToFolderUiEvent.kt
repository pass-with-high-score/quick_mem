package com.pwhs.quickmem.presentation.app.folder.add_study_set

import androidx.annotation.StringRes

sealed class AddStudySetToFolderUiEvent {
    data object StudySetAddedToFolder : AddStudySetToFolderUiEvent()
    data class Error(@StringRes val message: Int) : AddStudySetToFolderUiEvent()
}