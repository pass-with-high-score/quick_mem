package com.pwhs.quickmem.presentation.app.study_set.studies.flip

sealed class FlipFlashCardUiAction {
    data class OnSwipe(val id: String) : FlipFlashCardUiAction()
    data class OnSwipeRight(val isSwipingRight: Boolean) : FlipFlashCardUiAction()
    data class OnSwipeLeft(val isSwipingLeft: Boolean) : FlipFlashCardUiAction()
    data class OnUpdateCountKnown(
        val isIncrease: Boolean,
        val flashCardId: String
    ) : FlipFlashCardUiAction()

    data class OnUpdateCountStillLearning(
        val isIncrease: Boolean,
        val flashCardId: String
    ) : FlipFlashCardUiAction()

    data class OnUpdateCardIndex(val index: Int) : FlipFlashCardUiAction()
    data object OnRestartClicked : FlipFlashCardUiAction()
    data object OnContinueLearningClicked : FlipFlashCardUiAction()
}