package com.pwhs.quickmem.presentation.app.study_set.study.flip_flashcard

sealed class FlipFlashCardUiAction {
    data class OnSwipe(val id: String) : FlipFlashCardUiAction()
    data class OnSwipeRight(val isSwipingRight: Boolean) : FlipFlashCardUiAction()
    data class OnUpdateCountKnown(val isIncrease: Boolean) : FlipFlashCardUiAction()
    data class OnSwipeLeft(val isSwipingLeft: Boolean) : FlipFlashCardUiAction()
    data class OnUpdateCountStillLearning(val isIncrease: Boolean) : FlipFlashCardUiAction()
    data class OnUpdateCardIndex(val index: Int) : FlipFlashCardUiAction()
}