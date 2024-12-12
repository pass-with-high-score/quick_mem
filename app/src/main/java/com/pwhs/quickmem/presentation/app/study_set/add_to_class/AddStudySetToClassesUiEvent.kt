package com.pwhs.quickmem.presentation.app.study_set.add_to_class

import androidx.annotation.StringRes

sealed class AddStudySetToClassesUiEvent {
    data object StudySetAddedToClasses : AddStudySetToClassesUiEvent()
    data class Error(@StringRes val message: Int) : AddStudySetToClassesUiEvent()
}