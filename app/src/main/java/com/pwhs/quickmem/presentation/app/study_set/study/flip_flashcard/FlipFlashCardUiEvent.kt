package com.pwhs.quickmem.presentation.app.study_set.study.flip_flashcard

sealed class FlipFlashCardUiEvent {
    data object Finished : FlipFlashCardUiEvent()
}