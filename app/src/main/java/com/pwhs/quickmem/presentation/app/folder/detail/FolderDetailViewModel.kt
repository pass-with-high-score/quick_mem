package com.pwhs.quickmem.presentation.app.folder.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.data.enums.LearnMode
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.folder.SaveRecentAccessFolderRequestModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.repository.FolderRepository
import com.pwhs.quickmem.presentation.app.folder.detail.FolderDetailUiEvent.*
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
        saveRecentAccessFolder(id)
    }

    fun onEvent(event: FolderDetailUiAction) {
        when (event) {
            is FolderDetailUiAction.Refresh -> {
                getFolderSetById(_uiState.value.id)
            }

            FolderDetailUiAction.DeleteFolder -> {
                if (_uiState.value.isOwner) {
                    _uiState.update { it.copy(isLoading = true) }
                    deleteFolder()
                } else {
                    _uiEvent.trySend(ShowError("You can't delete this folder"))
                }
            }

            FolderDetailUiAction.EditFolder -> {
                if (_uiState.value.isOwner) {
                    _uiEvent.trySend(NavigateToEditFolder)
                } else {
                    _uiEvent.trySend(ShowError("You can't edit this folder"))
                }
            }

            is FolderDetailUiAction.NavigateToLearn -> {
                when (event.learnMode) {
                    LearnMode.FLIP -> {
                        _uiEvent.trySend(OnNavigateToFlipFlashcard(event.isGetAll))
                    }

                    LearnMode.QUIZ -> {
                        _uiEvent.trySend(OnNavigateToQuiz(event.isGetAll))
                    }

                    LearnMode.TRUE_FALSE -> {
                        _uiEvent.trySend(OnNavigateToTrueFalse(event.isGetAll))
                    }

                    LearnMode.WRITE -> {
                        _uiEvent.trySend(OnNavigateToWrite(event.isGetAll))
                    }

                    else -> {
                        // Do nothing
                    }
                }
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
                            val totalFlashCards = calculateTotalFlashCards(data.studySets ?: emptyList())
                            _uiState.update {
                                it.copy(
                                    title = data.title,
                                    linkShareCode = data.linkShareCode ?: "",
                                    description = data.description,
                                    isPublic = data.isPublic,
                                    studySetCount = data.studySetCount,
                                    user = data.owner,
                                    studySets = data.studySets ?: emptyList(),
                                    createdAt = data.createdAt,
                                    updatedAt = data.updatedAt,
                                    isLoading = false,
                                    isOwner = isOwner,
                                    totalFlashCards = totalFlashCards
                                )
                            }
                        } ?: run {
                            _uiEvent.send(ShowError("Folder not found"))
                        }
                        Timber.d("")
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
                _uiEvent.send(ShowError("Please login again!"))
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
                            _uiEvent.send(FolderDeleted)
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

    private fun saveRecentAccessFolder(folderId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            val saveRecentAccessFolderRequestModel = SaveRecentAccessFolderRequestModel(
                userId = userId,
                folderId = folderId
            )
            folderRepository.saveRecentAccessFolder(token, saveRecentAccessFolderRequestModel)
                .collectLatest { resource ->
                    when (resource) {
                        is Resources.Loading -> {
                            Timber.d("Loading")
                        }

                        is Resources.Success -> {
                            Timber.d("Recent access folder saved")
                        }

                        is Resources.Error -> {
                            Timber.e(resource.message)
                        }
                    }
                }
        }
    }

    private fun calculateTotalFlashCards(studySets: List<GetStudySetResponseModel>): Int {
        var totalFlashCards = 0
        studySets.forEach { studySet ->
            totalFlashCards += studySet.flashcardCount
        }
        return totalFlashCards
    }
}