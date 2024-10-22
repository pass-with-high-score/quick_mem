package com.pwhs.quickmem.presentation.app.study_set.study.flip_flashcard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.data.FlipCardStatus
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.repository.FlashCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
class FlipFlashCardViewModel @Inject constructor(
    private val flashCardRepository: FlashCardRepository,
    private val tokenManager: TokenManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(FlipFlashCardUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<FlipFlashCardUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val studySetId = savedStateHandle.get<String>("studySetId") ?: ""
        val studySetTitle = savedStateHandle.get<String>("studySetTitle") ?: ""
        val studySetDescription = savedStateHandle.get<String>("studySetDescription") ?: ""
        val studySetCardCount = savedStateHandle.get<Int>("studySetCardCount") ?: 0
        val studySetColorId = savedStateHandle.get<Int>("studySetColorId") ?: 0
        val studySetSubjectId = savedStateHandle.get<Int>("studySetSubjectId") ?: 0
        _uiState.update {
            it.copy(
                studySetId = studySetId,
                studySetTitle = studySetTitle,
                studySetDescription = studySetDescription,
                studySetCardCount = studySetCardCount,
                studySetColor = ColorModel.defaultColors[studySetColorId],
                studySetSubject = SubjectModel.defaultSubjects[studySetSubjectId]
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
                Timber.d("UpdateCardIndex: ${event.index}")
                _uiState.update { it.copy(currentCardIndex = it.currentCardIndex + 1) }
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
                    isRight = true
                )
            }

            is FlipFlashCardUiAction.OnUpdateCountStillLearning -> {
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
                            isSwipingLeft = false
                        )
                    }
                }
                updateFlashCardFlipStatus(
                    isRight = false
                )
            }
        }
    }

    private fun getFlashCard() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            flashCardRepository.getFlashCardsByStudySetId(token, _uiState.value.studySetId)
                .collect { resource ->
                    when (resource) {
                        is Resources.Error -> {
                            Timber.e(resource.message)
                        }

                        is Resources.Loading -> {
                            Timber.d("Loading")
                            _uiState.update { it.copy(isLoading = true) }
                        }

                        is Resources.Success -> {
                            Timber.d("Success")
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    flashCardList = resource.data ?: emptyList()
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun updateFlashCardFlipStatus(
        isRight: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val currentIndex = _uiState.value.currentCardIndex - 1
            Timber.d("CurrentIndex: $currentIndex")
            val flashCardList = _uiState.value.flashCardList

            if (currentIndex in flashCardList.indices) {
                val id = flashCardList[currentIndex].id
                val flipStatus =
                    if (isRight) FlipCardStatus.KNOW.name else FlipCardStatus.STILL_LEARNING.name
                Timber.d("UpdateFlipStatus: $id, $flipStatus")
                flashCardRepository.updateFlipFlashCard(token, id, flipStatus)
                    .collect { resource ->
                        when (resource) {
                            is Resources.Error -> {
                                Timber.e(resource.message)
                            }

                            is Resources.Loading -> {
                                Timber.d("Loading")
                            }

                            is Resources.Success -> {
                                Timber.d("Success ${resource.data?.flipStatus}")
                            }
                        }
                    }
            } else {
                Timber.e("Invalid currentCardIndex: $currentIndex")
            }
        }
    }
}