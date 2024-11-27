package com.pwhs.quickmem.presentation.app.explore

import com.pwhs.quickmem.core.data.enums.DifficultyLevel
import com.pwhs.quickmem.core.data.enums.LanguageCode
import com.pwhs.quickmem.core.data.enums.QuestionType
import com.pwhs.quickmem.domain.model.streak.GetTopStreakResponseModel

data class ExploreUiState(
    val isLoading: Boolean = false,
    val ownerId: String = "",
    val topStreaks: List<GetTopStreakResponseModel> = emptyList(),
    val streakOwner: GetTopStreakResponseModel? = null,
    val rankOwner: Int? = null,
    // AI
    val description: String = "",
    val difficulty: DifficultyLevel = DifficultyLevel.EASY,
    val language: String = LanguageCode.EN.code,
    val numberOfFlashcards: Int = 15,
    val questionType: QuestionType = QuestionType.MULTIPLE_CHOICE,
    val title: String = "",
    val errorMessage: String = ""
)