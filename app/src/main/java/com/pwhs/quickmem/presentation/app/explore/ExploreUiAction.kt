package com.pwhs.quickmem.presentation.app.explore

import com.pwhs.quickmem.core.data.enums.DifficultyLevel
import com.pwhs.quickmem.core.data.enums.QuestionType

sealed class ExploreUiAction {
    data object RefreshTopStreaks : ExploreUiAction()
    data class OnTitleChanged(val title: String) : ExploreUiAction()
    data class OnDescriptionChanged(val description: String) : ExploreUiAction()
    data class OnNumberOfFlashcardsChange(val numberOfCards: Int) : ExploreUiAction()
    data class OnLanguageChanged(val language: String) : ExploreUiAction()
    data class OnQuestionTypeChanged(val questionType: QuestionType) : ExploreUiAction()
    data class OnDifficultyLevelChanged(val difficultyLevel: DifficultyLevel) : ExploreUiAction()
    data object OnCreateStudySet : ExploreUiAction()
    data object OnEarnCoins : ExploreUiAction()
}