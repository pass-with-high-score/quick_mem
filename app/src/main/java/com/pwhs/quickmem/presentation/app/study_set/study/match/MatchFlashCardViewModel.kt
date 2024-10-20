package com.pwhs.quickmem.presentation.app.study_set.study.match

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class MatchFlashCardViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MatchFlashCardUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<MatchFlashCardUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: MatchFlashCardUiEvent) {
        when (event) {
            else -> {}
        }
    }
}