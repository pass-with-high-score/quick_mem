package com.pwhs.quickmem.presentation.app.study_set.study.test

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class TestFlashCardViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(TestFlashCardUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<TestFlashCardUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TestFlashCardUiEvent) {
        when (event) {
            else -> {}
        }
    }
}