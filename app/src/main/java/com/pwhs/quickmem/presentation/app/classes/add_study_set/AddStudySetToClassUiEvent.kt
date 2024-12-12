package com.pwhs.quickmem.presentation.app.classes.add_study_set

import androidx.annotation.StringRes

sealed class AddStudySetToClassUiEvent {
    data object StudySetAddedToClass : AddStudySetToClassUiEvent()
    data class Error(@StringRes val message: Int) : AddStudySetToClassUiEvent()
}