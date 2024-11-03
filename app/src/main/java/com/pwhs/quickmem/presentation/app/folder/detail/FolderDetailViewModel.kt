package com.pwhs.quickmem.presentation.app.folder.detail

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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FolderDetailViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    savedStateHandle: SavedStateHandle,
    private val folderRepository: FolderRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FolderDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<FolderDetailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val id: String = savedStateHandle["id"] ?: ""
        Timber.d("FolderDetailViewModel: $id")
        _uiState.update { it.copy(id = id) }
        getFolderSetById(id)
    }

    fun onEvent(event: FolderDetailUiAction) {
        when (event) {
            is FolderDetailUiAction.Refresh -> {
                getFolderSetById(_uiState.value.id)
            }

            FolderDetailUiAction.DeleteFolder -> {
                Timber.d("OnDeleteFolderClicked")
            }

            FolderDetailUiAction.EditFolder -> {
                Timber.d("OnEditFolderClicked")
                _uiEvent.trySend(FolderDetailUiEvent.NavigateToEditFolder)
            }

            is FolderDetailUiAction.ResetProgress -> {
                Timber.d("OnResetProgressClicked: ${event.studySetId}")
            }
        }
    }

    private fun getFolderSetById(id: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: run {
                _uiEvent.send(FolderDetailUiEvent.ShowError("Please login again!"))
                return@launch
            }
            folderRepository.getFolderById(token, id).collectLatest { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        Timber.d("Loading")
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        resource.data?.let { data ->
                            _uiState.update {
                                it.copy(
                                    title = data.title,
                                    description = data.description,
                                    isPublic = data.isPublic,
                                    studySetCount = data.studySetCount,
                                    user = data.user,
                                    studySets = data.studySets ?: emptyList(),
                                    createdAt = data.createdAt,
                                    updatedAt = data.updatedAt,
                                    isLoading = false
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
}