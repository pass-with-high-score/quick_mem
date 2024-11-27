package com.pwhs.quickmem.presentation.app.study_set.studies.flip

sealed class FlipFlashCardUiEvent {
    data object Finished : FlipFlashCardUiEvent()
    data object Back : FlipFlashCardUiEvent()
}