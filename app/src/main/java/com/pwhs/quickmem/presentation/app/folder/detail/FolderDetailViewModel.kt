package com.pwhs.quickmem.presentation.app.folder.detail

import androidx.lifecycle.ViewModel
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class FolderDetailViewModel @Inject constructor (
    private val tokenManager: TokenManager,
    private val appManager: AppManager
): ViewModel(){
    private val _uiState = MutableStateFlow(FolderDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<FolderDetailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: FolderDetailUiAction){
        when(event){
            else -> {}
        }
    }
}