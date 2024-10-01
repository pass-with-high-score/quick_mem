package com.pwhs.quickmem.presentation.app.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    private val _state = mutableStateOf(HomeUIState())
    val state: State<HomeUIState> = _state

    private val _uiEvent = Channel<HomeUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    // Function to handle UI events
    fun onEvent(event: HomeUIEvent) {
        when (event) {
            HomeUIEvent.NavigateFreeTrial -> navigateFreeTrial()
            HomeUIEvent.NavigateShowMoreCategories -> navigateShowMoreCategories()
            HomeUIEvent.NavigateShowMoreClasses -> navigateShowMoreClasses()
            HomeUIEvent.NavigateShowMoreFolder -> navigateShowMoreFolder()
            HomeUIEvent.NavigateShowMoreSets -> navigateShowMoreSets()
            HomeUIEvent.NavigateToSearch -> navigateSearch()
        }
    }

    private fun navigateSearch() {
        viewModelScope.launch {
            _uiEvent.trySend(HomeUIEvent.NavigateToSearch)
        }
    }

    private fun navigateFreeTrial() {
        viewModelScope.launch {
            _uiEvent.trySend(HomeUIEvent.NavigateFreeTrial)
        }
    }

    private fun navigateShowMoreSets() {
        viewModelScope.launch {
            _uiEvent.trySend(HomeUIEvent.NavigateShowMoreSets)
        }
    }

    private fun navigateShowMoreFolder() {
        viewModelScope.launch {
            _uiEvent.trySend(HomeUIEvent.NavigateShowMoreFolder)
        }
    }

    private fun navigateShowMoreClasses() {
        viewModelScope.launch {
            _uiEvent.trySend(HomeUIEvent.NavigateShowMoreClasses)
        }
    }

    private fun navigateShowMoreCategories() {
        viewModelScope.launch {
            _uiEvent.trySend(HomeUIEvent.NavigateShowMoreCategories)
        }
    }

    fun updateHasData(hasData: Boolean) {
        _state.value = _state.value.copy(hasData = hasData)
    }
}