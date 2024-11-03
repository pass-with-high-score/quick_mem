package com.pwhs.quickmem.presentation.app.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<SearchUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchUiAction) {
        when (event) {
            is SearchUiAction.Search -> {
                if (event.query.isNotBlank()) {
                    _uiEvent.trySend(SearchUiEvent.NavigateToResult(event.query))
                }
            }

            is SearchUiAction.OnQueryChanged -> {
                _uiState.update {
                    it.copy(query = event.query)
                }
            }
        }
    }
}