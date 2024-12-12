package com.pwhs.quickmem.presentation.app.classes.add_folder

import androidx.annotation.StringRes

sealed class AddFolderToClassUiEvent {
    data class ShowError(@StringRes val message: Int) : AddFolderToClassUiEvent()
    data object StudySetAddedToClass : AddFolderToClassUiEvent()
}