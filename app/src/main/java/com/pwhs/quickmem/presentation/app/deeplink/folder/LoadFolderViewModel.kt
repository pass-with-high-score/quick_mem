package com.pwhs.quickmem.presentation.app.deeplink.folder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.FolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadFolderViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val tokenManager: TokenManager,
    private val folderRepository: FolderRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoadFolderUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoadFolderUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val folderCode = saveStateHandle.get<String>("folderCode") ?: ""
        val type = saveStateHandle.get<String>("type") ?: ""

        _uiState.update {
            it.copy(
                folderCode = folderCode,
                type = type
            )
        }
        loadFolder()
    }

    private fun loadFolder() {
        viewModelScope.launch {
            val folderCode = uiState.value.folderCode
            val type = uiState.value.type
            val token = tokenManager.accessToken.firstOrNull()

            if (folderCode.isEmpty() || type.isEmpty() || token == null) {
                _uiEvent.send(LoadFolderUiEvent.UnAuthorized)
                return@launch
            }

            folderRepository.getFolderByLinkCode(token, folderCode).collect { resource ->
                when (resource) {
                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _uiEvent.send(LoadFolderUiEvent.Error)
                    }

                    is Resources.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                folderId = resource.data?.id
                            )
                        }
                        _uiEvent.send(LoadFolderUiEvent.FolderLoaded(resource.data?.id ?: ""))
                    }
                }
            }
        }
    }
}