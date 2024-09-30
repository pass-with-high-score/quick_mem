package com.pwhs.quickmem.presentation.app.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.presentation.auth.login.LoginUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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
            HomeUIEvent.NavigateFreeTrial -> NavigateFreeTrial()
            HomeUIEvent.NavigateShowMoreCategories -> NavigateShowMoreCategories()
            HomeUIEvent.NavigateShowMoreClasses -> NavigateShowMoreClasses()
            HomeUIEvent.NavigateShowMoreFolder -> NavigateShowMoreFolder()
            HomeUIEvent.NavigateShowMoreSets -> NavigateShowMoreSets()
            HomeUIEvent.NavigateToSearch -> NavigateSearch()
        }
    }

    private fun NavigateSearch() {
        viewModelScope.launch {
            _uiEvent.trySend(HomeUIEvent.NavigateToSearch)
        }
    }

    private fun NavigateFreeTrial() {
        viewModelScope.launch {
            _uiEvent.trySend(HomeUIEvent.NavigateFreeTrial)
        }
    }

    private fun NavigateShowMoreSets() {
        viewModelScope.launch {
            _uiEvent.trySend(HomeUIEvent.NavigateShowMoreSets)
        }
    }

    private fun NavigateShowMoreFolder() {
        viewModelScope.launch {
            _uiEvent.trySend(HomeUIEvent.NavigateShowMoreFolder)
        }
    }

    private fun NavigateShowMoreClasses() {
        viewModelScope.launch {
            _uiEvent.trySend(HomeUIEvent.NavigateShowMoreClasses)
        }
    }

    private fun NavigateShowMoreCategories() {
        viewModelScope.launch {
            _uiEvent.trySend(HomeUIEvent.NavigateShowMoreCategories)
        }
    }

    fun updateHasData(hasData: Boolean) {
        _state.value = _state.value.copy(hasData = hasData)
    }
}