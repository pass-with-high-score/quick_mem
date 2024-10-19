package com.pwhs.quickmem.presentation.app.flashcard.edit

sealed class EditFlashCardUiEvent {
    data object LoadImage : EditFlashCardUiEvent()
    data object FlashCardSaved : EditFlashCardUiEvent()
    data object FlashCardSaveError : EditFlashCardUiEvent()
}