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
            FolderDetailUiAction.OnDeleteFolderClicked -> {
                Timber.d("OnDeleteFolderClicked")
            }
            FolderDetailUiAction.OnEditFolderClicked -> {
                Timber.d("OnEditFolderClicked")
                _uiEvent.trySend(FolderDetailUiEvent.NavigateToEditFolder)
            }
            is FolderDetailUiAction.OnResetProgressClicked -> {
                Timber.d("OnResetProgressClicked: ${event.studySetId}")
            }
        }
    }

    private fun getFolderSetById(id: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            folderRepository.getFolderById(token, id).collectLatest { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        Timber.d("Loading")
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                title = resource.data!!.title,
                                description = resource.data.description,
                                isPublic = resource.data.isPublic,
                                studySetCount = resource.data.studySetCount,
                                ownerId = resource.data.ownerId,
                                user = resource.data.user,
                                studySets = resource.data.studySets,
                                createdAt = resource.data.createdAt,
                                updatedAt = resource.data.updatedAt,
                                isLoading = false
                            )
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