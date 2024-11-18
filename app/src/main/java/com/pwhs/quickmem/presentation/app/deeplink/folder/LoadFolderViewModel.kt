package com.pwhs.quickmem.presentation.app.deeplink.folder

import androidx.lifecycle.ViewModel
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.domain.repository.FolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class LoadFolderViewModel @Inject constructor(
    private val appManager: AppManager,
    private val tokenManager: TokenManager,
    private val folderRepository: FolderRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(LoadFolderUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoadFolderUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
}