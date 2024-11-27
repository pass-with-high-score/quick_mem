package com.pwhs.quickmem.presentation.app.study_set.studies.flip

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.enums.FlipCardStatus
import com.pwhs.quickmem.core.data.enums.LearnMode
import com.pwhs.quickmem.core.data.enums.ResetType
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.study_time.CreateStudyTimeModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.repository.FlashCardRepository
import com.pwhs.quickmem.domain.repository.StudySetRepository
import com.pwhs.quickmem.domain.repository.StudyTimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FlipFlashCardViewModel @Inject constructor(
    private val flashCardRepository: FlashCardRepository,
    private val studySetRepository: StudySetRepository,
    private val studyTimeRepository: StudyTimeRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(FlipFlashCardUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<FlipFlashCardUiEvent>()
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

    fun onEvent(event: FlipFlashCardUiAction) {
        when (event) {
            is FlipFlashCardUiAction.OnSwipe -> {
                // remove the flash card from the list
                val flashCardList = _uiState.value.flashCardList.toMutableList()
                val index = flashCardList.indexOfFirst { it.id == event.id }
                if (index != -1) {
                    flashCardList.removeAt(index)
                    _uiState.update { it.copy(flashCardList = flashCardList) }
                }
            }

            is FlipFlashCardUiAction.OnUpdateCardIndex -> {
                Timber.d("OnUpdateCardIndex: ${event.index}")
                _uiState.update {
                    it.copy(
                        currentCardIndex = it.currentCardIndex + 1,
                    )
                }
                if (event.index == _uiState.value.flashCardList.size - 1) {
                    _uiState.update {
                        it.copy(
                            isEndOfList = true,
                            learningTime = System.currentTimeMillis() - it.startTime
                        )
                    }
                    playCompleteSound()
                    sendCompletedStudyTime()
                    return
                }
            }

            is FlipFlashCardUiAction.OnSwipeLeft -> {
                _uiState.update { it.copy(isSwipingLeft = event.isSwipingLeft) }
            }

            is FlipFlashCardUiAction.OnSwipeRight -> {
                _uiState.update { it.copy(isSwipingRight = event.isSwipingRight) }
            }

            is FlipFlashCardUiAction.OnUpdateCountKnown -> {
                if (event.isIncrease) {
                    _uiState.update {
                        it.copy(
                            countKnown = it.countKnown + 1,
                            isSwipingRight = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            countKnown = it.countKnown - 1,
                            isSwipingRight = false
                        )
                    }
                }
                updateFlashCardFlipStatus(
                    isRight = true,
                    event.flashCardId
                )
            }

            is FlipFlashCardUiAction.OnUpdateCountStillLearning -> {
                Timber.d("OnUpdateCountStillLearninggggg")
                if (event.isIncrease) {
                    _uiState.update {
                        it.copy(
                            countStillLearning = it.countStillLearning + 1,
                            isSwipingLeft = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            countStillLearning = it.countStillLearning - 1,
                            isSwipingLeft = false,
                        )
                    }
                }
                updateFlashCardFlipStatus(
                    isRight = false,
                    event.flashCardId
                )
            }

            FlipFlashCardUiAction.OnRestartClicked -> {
                restartStudySet()
            }

            FlipFlashCardUiAction.OnContinueLearningClicked -> {
                _uiState.update {
                    it.copy(
                        isEndOfList = false,
                        countKnown = 0,
                        countStillLearning = 0,
                        currentCardIndex = 0,
                        startTime = System.currentTimeMillis(),
                        flashCardList = emptyList()
                    )
                }

                getFlashCard()
            }

            is FlipFlashCardUiAction.OnBackClicked -> {
                _uiState.update {
                    it.copy(
                        learningTime = System.currentTimeMillis() - it.startTime
                    )
                }
                sendCompletedStudyTime()
                _uiEvent.trySend(FlipFlashCardUiEvent.Back)
            }
        }
    }

    private fun getFlashCard() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            flashCardRepository.getFlashCardsByStudySetId(
                token = token,
                studySetId = _uiState.value.studySetId,
                learnMode = LearnMode.FLIP
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
                                    isLoading = false,
                                    isEndOfList = true,
                                    learningTime = 0,
                                )
                            }
                            playCompleteSound()
                            return@collect
                        }

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                flashCardList = resource.data
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateFlashCardFlipStatus(
        isRight: Boolean,
        flashCardId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val flipStatus =
                if (isRight) FlipCardStatus.KNOW.name else FlipCardStatus.STILL_LEARNING.name
            flashCardRepository.updateFlipFlashCard(token, flashCardId, flipStatus)
                .collect { resource ->
                    when (resource) {
                        is Resources.Error -> {
                        }

                        is Resources.Loading -> {
                        }

                        is Resources.Success -> {
                        }
                    }
                }
        }
    }

    private fun restartStudySet() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            studySetRepository.resetProgress(
                token,
                _uiState.value.studySetId,
                resetType = ResetType.FLIP_STATUS.type
            )
                .collect { resource ->
                    when (resource) {
                        is Resources.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isEndOfList = true,
                                    learningTime = 0,
                                )
                            }
                        }

                        is Resources.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }

                        is Resources.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isEndOfList = false,
                                    countKnown = 0,
                                    countStillLearning = 0,
                                    currentCardIndex = 0,
                                    startTime = System.currentTimeMillis(),
                                    flashCardList = emptyList()
                                )
                            }
                            getFlashCard()
                        }
                    }
                }
        }
    }

    private fun playCompleteSound() {
        val mediaPlayer = MediaPlayer.create(getApplication(), R.raw.study_complete)
        mediaPlayer.start()
    }

    private fun sendCompletedStudyTime() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            val createStudyTimeModel = CreateStudyTimeModel(
                learnMode = LearnMode.FLIP.mode,
                studySetId = _uiState.value.studySetId,
                timeSpent = _uiState.value.learningTime.toInt(),
                userId = userId
            )
            studyTimeRepository.createStudyTime(token, createStudyTimeModel)
                .collect()
        }
    }
}