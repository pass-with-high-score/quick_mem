package com.pwhs.quickmem.presentation.app.classes.edit

sealed class EditClassUiEvent {
    data class ShowError(val message: String) : EditClassUiEvent()
    data class ClassesUpdated(val id: String) : EditClassUiEvent()
}