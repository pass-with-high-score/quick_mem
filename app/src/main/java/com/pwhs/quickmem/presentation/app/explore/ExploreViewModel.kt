package com.pwhs.quickmem.presentation.app.explore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.data.enums.DifficultyLevel
import com.pwhs.quickmem.core.data.enums.QuestionType
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.study_set.CreateStudySetByAIRequestModel
import com.pwhs.quickmem.domain.repository.StreakRepository
import com.pwhs.quickmem.domain.repository.StudySetRepository
import com.pwhs.quickmem.util.getLanguageCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    private val streakRepository: StreakRepository,
    private val studySetRepository: StudySetRepository,
    application: Application
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ExploreUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val languageCode = getApplication<Application>().getLanguageCode()
        viewModelScope.launch {
            val userId = appManager.userId.firstOrNull() ?: ""
            _uiState.update {
                it.copy(
                    ownerId = userId,
                    language = languageCode
                )
            }
        }
        getTopStreaks()
    }

    fun onEvent(event: ExploreUiAction) {
        when (event) {
            ExploreUiAction.RefreshTopStreaks -> {
                getTopStreaks()
            }

            is ExploreUiAction.OnDescriptionChanged -> {
                _uiState.update { it.copy(description = event.description) }
            }

            is ExploreUiAction.OnDifficultyLevelChanged -> {
                _uiState.update { it.copy(difficulty = event.difficultyLevel) }
            }

            is ExploreUiAction.OnLanguageChanged -> {
                _uiState.update { it.copy(language = event.language) }
            }

            is ExploreUiAction.OnNumberOfFlashcardsChange -> {
                _uiState.update {
                    it.copy(
                        numberOfFlashcards = event.numberOfCards
                    )
                }
            }

            is ExploreUiAction.OnQuestionTypeChanged -> {
                _uiState.update { it.copy(questionType = event.questionType) }
            }

            is ExploreUiAction.OnTitleChanged -> {
                _uiState.update { it.copy(title = event.title) }
            }

            is ExploreUiAction.OnCreateStudySet -> {
                if (uiState.value.title.isEmpty()) {
                    _uiState.update {
                        it.copy(errorMessage = "Title is required")
                    }
                } else {
                    createStudySet()
                }
            }
        }
    }

    private fun getTopStreaks() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            streakRepository.getTopStreaks(token, 10).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        val topStreaks = resource.data ?: emptyList()
                        val streakOwner = topStreaks.find { it.userId == uiState.value.ownerId }
                        val rankOwner =
                            topStreaks.indexOfFirst { it.userId == uiState.value.ownerId }
                                .takeIf { it != -1 }?.plus(1)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                topStreaks = topStreaks,
                                streakOwner = streakOwner,
                                rankOwner = rankOwner
                            )
                        }
                    }

                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _uiEvent.send(ExploreUiEvent.Error(resource.message ?: ""))
                    }
                }
            }
        }
    }

    private fun createStudySet() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            val createStudySetByAIRequestModel = CreateStudySetByAIRequestModel(
                title = uiState.value.title,
                description = uiState.value.description,
                difficulty = uiState.value.difficulty.level,
                language = uiState.value.language,
                numberOfFlashcards = uiState.value.numberOfFlashcards,
                questionType = uiState.value.questionType.type,
                userId = userId
            )
            studySetRepository.createStudySetByAI(
                token = token,
                createStudySetByAIRequestModel
            ).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "",
                                title = "",
                                description = "",
                                numberOfFlashcards = 10,
                                questionType = QuestionType.MULTIPLE_CHOICE,
                                difficulty = DifficultyLevel.EASY,
                                language = getApplication<Application>().getLanguageCode()
                            )
                        }
                        _uiEvent.send(ExploreUiEvent.CreatedStudySet(resource.data?.id ?: ""))
                    }

                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = resource.message ?: ""
                            )
                        }
                        _uiEvent.send(ExploreUiEvent.Error(resource.message ?: ""))
                    }
                }
            }
        }
    }
}