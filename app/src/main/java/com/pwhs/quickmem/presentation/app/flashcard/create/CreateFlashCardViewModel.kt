package com.pwhs.quickmem.presentation.app.flashcard.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateFlashCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateFlashCardUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<CreateFlashCardUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val studySetId: String = savedStateHandle["studySetId"] ?: ""
        val studySetTitle: String = savedStateHandle["studySetTitle"] ?: ""
        _uiState.update { it.copy(studySetId = studySetId, studySetTitle = studySetTitle) }
    }

    fun onEvent(event: CreateFlashCardUiAction) {
        when (event) {
            is CreateFlashCardUiAction.AnswerChanged -> {
                _uiState.update { it.copy(answer = event.answer) }
            }

            is CreateFlashCardUiAction.AnswerImageUrlChanged -> {
                _uiState.update { it.copy(answerImageUrl = event.answerImageUrl) }
            }

            CreateFlashCardUiAction.CancelClicked -> TODO()
            is CreateFlashCardUiAction.QuestionChanged -> {
                _uiState.update { it.copy(question = event.question) }
            }

            is CreateFlashCardUiAction.QuestionImageUrlChanged -> {
                _uiState.update { it.copy(questionImageUrl = event.questionImageUrl) }
            }

            CreateFlashCardUiAction.SaveFlashCardClicked -> TODO()
            is CreateFlashCardUiAction.StudySetIdChanged -> {
                _uiState.update { it.copy(studySetId = event.studySetId) }
            }

            is CreateFlashCardUiAction.StudySetTitleChanged -> {
                _uiState.update { it.copy(studySetTitle = event.studySetTitle) }
            }
        }
    }

}