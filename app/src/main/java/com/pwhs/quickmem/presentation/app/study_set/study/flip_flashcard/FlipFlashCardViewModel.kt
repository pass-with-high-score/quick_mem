package com.pwhs.quickmem.presentation.app.study_set.study.flip_flashcard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class FlipFlashCardViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(FlipFlashCardUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<FlipFlashCardUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: FlipFlashCardUiEvent) {
        when (event) {
            else -> {}
        }
    }
}