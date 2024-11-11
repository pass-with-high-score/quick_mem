package com.pwhs.quickmem.presentation.app.classes.add_folder

sealed class AddFolderToClassUIEvent {
    data class ShowError(val message: String):AddFolderToClassUIEvent()
    data object StudySetAddedToClass : AddFolderToClassUIEvent()
}