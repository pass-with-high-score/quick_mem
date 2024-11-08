package com.pwhs.quickmem.presentation.app.folder.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.FolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FolderDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    private val folderRepository: FolderRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FolderDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<FolderDetailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val id: String = savedStateHandle["id"] ?: ""
        _uiState.update { it.copy(id = id) }
        getFolderSetById(id)
    }

    fun onEvent(event: FolderDetailUiAction) {
        when (event) {
            is FolderDetailUiAction.Refresh -> {
                getFolderSetById(_uiState.value.id)
            }

            FolderDetailUiAction.DeleteFolder -> {
                deleteFolder()
            }

            FolderDetailUiAction.EditFolder -> {
                _uiEvent.trySend(FolderDetailUiEvent.NavigateToEditFolder)
            }
        }
    }

    private fun getFolderSetById(id: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            folderRepository.getFolderById(token, id).collectLatest { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        resource.data?.let { data ->
                            val isOwner = appManager.userId.firstOrNull() == data.owner.id
                            _uiState.update {
                                it.copy(
                                    title = data.title,
                                    description = data.description,
                                    isPublic = data.isPublic,
                                    studySetCount = data.studySetCount,
                                    user = data.owner,
                                    studySets = data.studySets ?: emptyList(),
                                    createdAt = data.createdAt,
                                    updatedAt = data.updatedAt,
                                    isLoading = false,
                                    isOwner = isOwner
                                )
                            }
                        } ?: run {
                            _uiEvent.send(FolderDetailUiEvent.ShowError("Folder not found"))
                        }
                    }

                    is Resources.Error -> {
                        Timber.e(resource.message)
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }

    private fun deleteFolder() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: run {
                _uiEvent.send(FolderDetailUiEvent.ShowError("Please login again!"))
                return@launch
            }
            folderRepository.deleteFolder(token, _uiState.value.id).collectLatest { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        Timber.d("Loading")
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        resource.data?.let {
                            Timber.d("Folder deleted")
                            _uiState.update { it.copy(isLoading = false) }
                            _uiEvent.send(FolderDetailUiEvent.FolderDeleted)
                        }
                    }

                    is Resources.Error -> {
                        Timber.e(resource.message)
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }
}