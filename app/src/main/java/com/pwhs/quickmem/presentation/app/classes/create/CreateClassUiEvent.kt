package com.pwhs.quickmem.presentation.app.classes.create

sealed class CreateClassUiEvent {
    data class ClassesCreated(val id: String) : CreateClassUiEvent()
    data class ShowError(val message: String) : CreateClassUiEvent()
}