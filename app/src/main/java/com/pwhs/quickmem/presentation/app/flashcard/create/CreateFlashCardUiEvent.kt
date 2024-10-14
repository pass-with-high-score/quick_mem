package com.pwhs.quickmem.presentation.app.flashcard.create

sealed class CreateFlashCardUiEvent {
    data object SaveFlashCardClicked : CreateFlashCardUiEvent()
    data object CancelClicked : CreateFlashCardUiEvent()
}