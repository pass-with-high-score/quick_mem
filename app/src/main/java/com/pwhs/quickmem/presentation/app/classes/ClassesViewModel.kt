package com.pwhs.quickmem.presentation.app.classes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassesViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(ClassesUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ClassesUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val joinClassCode: String = savedStateHandle["code"] ?: ""
        viewModelScope.launch {
            appManager.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    _uiState.update { it.copy(isLogin = true) }
                } else {
                    _uiState.update { it.copy(isLogin = false) }
                    onEvent(ClassesUiAction.NavigateToWelcomeClicked)
                }
            }
        }
    }

    fun onEvent(event: ClassesUiAction) {
        when (event) {
            ClassesUiAction.JoinClassClicked -> {
                TODO()
            }

            ClassesUiAction.NavigateToWelcomeClicked -> {
                _uiEvent.trySend(ClassesUiEvent.NavigateToWelcome)
            }
        }
    }
}