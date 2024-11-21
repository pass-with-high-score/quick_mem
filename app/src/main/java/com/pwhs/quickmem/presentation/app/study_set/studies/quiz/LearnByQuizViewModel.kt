package com.pwhs.quickmem.presentation.app.study_set.studies.quiz

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.enums.LearnMode
import com.pwhs.quickmem.core.data.enums.QuizStatus
import com.pwhs.quickmem.core.data.enums.ResetType
import com.pwhs.quickmem.core.data.states.RandomAnswer
import com.pwhs.quickmem.core.data.states.WrongAnswer
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
class LearnByQuizViewModel @Inject constructor(
    private val flashCardRepository: FlashCardRepository,
    private val studySetRepository: StudySetRepository,
    private val tokenManager: TokenManager,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(LearnFlashCardUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LearnByQuizUiEvent>()
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

    fun onEvent(event: LearnByQuizUiAction) {
        when (event) {
            is LearnByQuizUiAction.LoadNextFlashCard -> {
                loadNextFlashCard(
                    _uiState.value.nextFlashCard,
                )
            }

            is LearnByQuizUiAction.SubmitCorrectAnswer -> submitCorrectAnswer(
                event.flashCardId,
                event.quizStatus,
                event.userAnswer
            )

            LearnByQuizUiAction.ContinueLearnWrongAnswer -> {
                // reset all state
                _uiState.update {
                    it.copy(
                        currentCardIndex = 0,
                        learningTime = 0,
                        startTime = System.currentTimeMillis(),
                        isEndOfList = false,
                        wrongAnswerCount = 0,
                        listWrongAnswer = emptyList()
                    )
                }
                getFlashCard()
            }

            LearnByQuizUiAction.RestartLearn -> {
                viewModelScope.launch {
                    val token = tokenManager.accessToken.firstOrNull() ?: ""
                    val studySetId = _uiState.value.studySetId
                    studySetRepository.resetProgress(
                        token,
                        studySetId,
                        ResetType.QUIZ_STATUS.type
                    ).collect { resource ->
                        when (resource) {
                            is Resources.Error -> {
                                Timber.e(resource.message)
                                _uiState.update { it.copy(isLoading = false) }
                            }

                            is Resources.Loading -> {
                                Timber.d("Loading")
                                _uiState.update { it.copy(isLoading = true) }
                            }

                            is Resources.Success -> {
                                // reset all state
                                _uiState.update {
                                    it.copy(
                                        currentCardIndex = 0,
                                        learningTime = 0,
                                        startTime = System.currentTimeMillis(),
                                        isEndOfList = false,
                                        wrongAnswerCount = 0,
                                        listWrongAnswer = emptyList()
                                    )
                                }
                                getFlashCard()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getFlashCard() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            flashCardRepository.getFlashCardsByStudySetId(
                token = token,
                studySetId = _uiState.value.studySetId,
                learnMode = LearnMode.QUIZ
            ).collect { resource ->
                when (resource) {
                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                    }

                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        if (resource.data.isNullOrEmpty()) {
                            _uiState.update {
                                it.copy(
                                    learningTime = 0,
                                    isLoading = false,
                                    isEndOfList = true
                                )
                            }
                            playCompleteSound()
                            _uiEvent.send(LearnByQuizUiEvent.Finished)
                            return@collect
                        }

                        val flashCards = resource.data
                        val currentCard = flashCards.firstOrNull()
                        val randomAnswers = generateRandomAnswers(flashCards, currentCard)

                        _uiState.update {
                            it.copy(
                                flashCardList = flashCards,
                                randomAnswers = randomAnswers,
                                isLoading = false,
                                currentFlashCard = currentCard,
                                nextFlashCard = flashCards.getOrNull(1)
                            )
                        }
                    }
                }

            }
        }
    }


    private fun submitCorrectAnswer(
        flashCardId: String,
        quizStatus: QuizStatus,
        userAnswer: String = ""
    ) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            when (quizStatus) {
                QuizStatus.CORRECT -> playCorrectSound()
                QuizStatus.WRONG -> playIncorrectSound()
                else -> {
                }
            }
            try {
                flashCardRepository.updateQuizStatus(
                    token = token,
                    id = flashCardId,
                    quizStatus = quizStatus.status
                ).collect { resource ->
                    when (resource) {
                        is Resources.Error -> {
                            Timber.e(resource.message)
                        }

                        is Resources.Loading -> {
                            Timber.d("Loading")
                        }

                        is Resources.Success -> {

                            // mark the current card as answered
                            val flashCardList = _uiState.value.flashCardList.map {
                                if (it.id == flashCardId) {
                                    it.copy(isAnswered = true)
                                } else {
                                    it
                                }
                            }

                            // get the next flash card (one that is not answered)
                            val nextFlashCard = flashCardList.firstOrNull { !it.isAnswered }

                            _uiState.update {
                                it.copy(
                                    flashCardList = flashCardList,
                                    nextFlashCard = nextFlashCard,
                                    wrongAnswerCount = if (quizStatus == QuizStatus.WRONG || quizStatus == QuizStatus.SKIPPED) it.wrongAnswerCount + 1 else it.wrongAnswerCount,
                                    listWrongAnswer = if (quizStatus == QuizStatus.WRONG) it.listWrongAnswer + WrongAnswer(
                                        flashCard = it.currentFlashCard!!,
                                        userAnswer = userAnswer
                                    ) else it.listWrongAnswer
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

        answers.add(
            RandomAnswer(
                answer = currentCard.definition,
                isCorrect = true,
                imageURL = currentCard.definitionImageURL ?: ""
            )
        )
        return answers.shuffled()
    }

    private fun loadNextFlashCard(
        nextFlashCard: FlashCardResponseModel?,
    ) {
        if (nextFlashCard != null && _uiState.value.currentCardIndex < _uiState.value.flashCardList.size - 1) {
            val randomAnswers = generateRandomAnswers(_uiState.value.flashCardList, nextFlashCard)
            _uiState.update {
                it.copy(
                    currentCardIndex = it.currentCardIndex + 1,
                    randomAnswers = randomAnswers,
                    currentFlashCard = nextFlashCard,
                    nextFlashCard = it.flashCardList.getOrNull(it.currentCardIndex + 2)
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    learningTime = System.currentTimeMillis() - it.startTime,
                    isEndOfList = true
                )
            }
            playCompleteSound()
            _uiEvent.trySend(LearnByQuizUiEvent.Finished)
        }
    }

    private fun playCompleteSound() {
        val mediaPlayer = MediaPlayer.create(getApplication(), R.raw.finish)
        mediaPlayer.start()
    }

    private fun playCorrectSound() {
        val mediaPlayer = MediaPlayer.create(getApplication(), R.raw.correct)
        mediaPlayer.start()
    }

    private fun playIncorrectSound() {
        val mediaPlayer = MediaPlayer.create(getApplication(), R.raw.wrong)
        mediaPlayer.start()
    }
}