package com.pwhs.quickmem.presentation.app.flashcard.create

sealed class CreateFlashCardUiEvent {
    data object LoadImage : CreateFlashCardUiEvent()
    data object FlashCardSaved : CreateFlashCardUiEvent()
    data object FlashCardSaveError : CreateFlashCardUiEvent()
}