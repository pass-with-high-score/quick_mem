package com.pwhs.quickmem.presentation.app.classes.edit

sealed class EditClassUiEvent {
    data object ShowError: EditClassUiEvent()
    data object ClassesUpdated : EditClassUiEvent()
}