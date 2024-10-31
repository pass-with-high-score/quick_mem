package com.pwhs.quickmem.presentation.app.folder.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.folder.UpdateFolderRequestModel
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
import javax.inject.Inject

@HiltViewModel
class EditFolderViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    savedStateHandle: SavedStateHandle,
    private val folderRepository: FolderRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditFolderUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<EditFolderUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val folderId: String = savedStateHandle["folderId"] ?: ""
        val folderTitle = savedStateHandle["folderTitle"] ?: ""
        val folderDescription = savedStateHandle["folderDescription"] ?: ""
        val folderIsPublic: Boolean = savedStateHandle["folderIsPublic"] ?: false
        _uiState.update {
            it.copy(
                id = folderId,
                title = folderTitle,
                description = folderDescription,
                isPublic = folderIsPublic
            )
        }
    }

    fun onEvent(event: EditFolderUiAction) {
        when (event) {
            EditFolderUiAction.SaveClicked -> {
                val uiState = _uiState.value
                val trimmedTitle = uiState.title.trim()
                if (trimmedTitle.isEmpty()) {
                    _uiState.update {
                        it.copy(titleError = "Title is required")
                    }
                    return
                } else if (trimmedTitle.length < 3) {
                    _uiState.update {
                        it.copy(titleError = "Title must be at least 3 characters")
                    }
                    return
                } else {
                    _uiState.update {
                        it.copy(titleError = "")
                    }
                    saveFolder()
                }
            }

            is EditFolderUiAction.TitleChanged -> {
                _uiState.update {
                    it.copy(title = event.title)
                }
            }

            is EditFolderUiAction.DescriptionChanged -> {
                _uiState.update {
                    it.copy(description = event.description)
                }
            }

            is EditFolderUiAction.IsPublicChanged -> {
                _uiState.update {
                    it.copy(isPublic = event.isPublic)
                }
            }
        }
    }

    private fun saveFolder() {
        viewModelScope.launch {
            val updateFolderRequestModel = UpdateFolderRequestModel(
                title = uiState.value.title,
                description = uiState.value.description,
                isPublic = uiState.value.isPublic
            )
            folderRepository.updateFolder(
                token = tokenManager.accessToken.firstOrNull() ?: run {
                    _uiEvent.send(EditFolderUiEvent.ShowError("Please login again!"))
                    return@launch
                },
                folderId = uiState.value.id,
                updateFolderRequestModel
            ).collectLatest { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                        resource.data?.let { data ->
                            _uiEvent.send(EditFolderUiEvent.FolderEdited(data.id))
                        }?: run {
                            _uiEvent.send(EditFolderUiEvent.ShowError("Failed to update folder"))
                        }
                    }
                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                        _uiEvent.send(EditFolderUiEvent.ShowError(resource.message!!))
                    }
                }

            }
        }
    }
}