package com.pwhs.quickmem.presentation.app.study_set.study.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.data.LearnMode
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.flashcard.FlashCardResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.repository.FlashCardRepository
import com.pwhs.quickmem.domain.repository.StudySetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LearnFlashCardViewModel @Inject constructor(
    private val flashCardRepository: FlashCardRepository,
    private val studySetRepository: StudySetRepository,
    private val tokenManager: TokenManager,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LearnFlashCardUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LearnFlashCardUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val studySetId = savedStateHandle.get<String>("studySetId") ?: ""
        val studySetTitle = savedStateHandle.get<String>("studySetTitle") ?: ""
        val studySetDescription = savedStateHandle.get<String>("studySetDescription") ?: ""
        val studySetColorId = savedStateHandle.get<Int>("studySetColorId") ?: 0
        val studySetSubjectId = savedStateHandle.get<Int>("studySetSubjectId") ?: 0
        _uiState.update {
            it.copy(
                studySetId = studySetId,
                studySetTitle = studySetTitle,
                studySetDescription = studySetDescription,
                studySetColor = ColorModel.defaultColors[studySetColorId],
                studySetSubject = SubjectModel.defaultSubjects[studySetSubjectId],
                startTime = System.currentTimeMillis()
            )
        }

        getFlashCard()
    }

    fun onEvent(event: LearnFlashCardUiAction) {
        when (event) {
            LearnFlashCardUiAction.LoadNextFlashCard -> loadNextFlashCard()
            is LearnFlashCardUiAction.SubmitCorrectAnswer -> submitCorrectAnswer(event.flashCardId)
        }
    }

    private fun getFlashCard() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            flashCardRepository.getFlashCardsByStudySetId(
                token = token,
                studySetId = _uiState.value.studySetId,
                learnMode = LearnMode.normal
            ).collect { resource ->
                when (resource) {
                    is Resources.Error -> {
                    }

                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        if (resource.data.isNullOrEmpty()) {
                            _uiState.update {
                                it.copy(
                                    learningTime = 0,
                                )
                            }
                            return@collect
                        }

                        val flashCards = resource.data
                        val flashCardLearnRound = flashCards.take(minOf(flashCards.size, 7))
                        val currentCard =
                            flashCardLearnRound.getOrNull(_uiState.value.flashCardLearnRoundIndex)
                        val randomAnswers = generateRandomAnswers(flashCards, currentCard)
                        Timber.d("FlashCardLearnRound: $flashCardLearnRound")
                        Timber.d("RandomAnswers: $randomAnswers")

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                flashCardList = flashCards,
                                flashCardLearnRound = flashCardLearnRound,
                                randomAnswers = randomAnswers
                            )
                        }
                    }
                }

            }
        }
    }

    private fun loadNextFlashCard() {
        _uiState.update {
            val nextIndex = it.flashCardLearnRoundIndex + 1
            it.copy(flashCardLearnRoundIndex = nextIndex)
        }
    }

    private fun submitCorrectAnswer(flashCardId: String) {
        Timber.d("SubmitCorrectAnswer: $flashCardId")
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val currentCard =
                _uiState.value.flashCardLearnRound.firstOrNull { it.id == flashCardId }
            val rating = when (currentCard?.rating) {
                "NOT_STUDIED" -> "STILL_LEARNING"
                "STILL_LEARNING" -> "MASTERED"
                else -> "MASTERED"
            }
            try {
                flashCardRepository.updateFlashCardRating(token, flashCardId, rating)
                    .collect { resource ->
                        when (resource) {
                            is Resources.Error -> {
                                Timber.e("Error updating flash card rating")
                            }

                            is Resources.Loading -> {
                                Timber.d("Loading updating flash card rating")
                            }

                            is Resources.Success -> {
                                Timber.d("Success updating flash card rating")
                                loadNextFlashCard()
                                val randomAnswers = generateRandomAnswers(
                                    _uiState.value.flashCardList,
                                    _uiState.value.flashCardLearnRound.getOrNull(_uiState.value.flashCardLearnRoundIndex)
                                )
                                _uiState.update {
                                    it.copy(
                                        randomAnswers = randomAnswers
                                    )
                                }
                            }
                        }
                    }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun generateRandomAnswers(
        flashCards: List<FlashCardResponseModel>,
        currentCard: FlashCardResponseModel?
    ): List<RandomAnswer> {
        if (currentCard == null) return emptyList()

        val answers = flashCards
            .filter { it.id != currentCard.id }
            .distinctBy { it.definition }
            .shuffled()
            .take(3)
            .map {
                RandomAnswer(
                    answer = it.definition,
                    isCorrect = false,
                    imageURL = it.definitionImageURL ?: ""
                )
            }
            .toMutableList()

        answers.add(RandomAnswer(answer = currentCard.definition, isCorrect = true))
        return answers.shuffled()
    }

}