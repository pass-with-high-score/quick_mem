package com.pwhs.quickmem.presentation.app.folder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(FolderUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<FolderUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: FolderUiAction) {
        when (event) {

            else -> {}
        }
    }
}