package com.pwhs.quickmem.presentation.app.folder.add_study_set

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.study_set.AddStudySetToFolderRequestModel
import com.pwhs.quickmem.domain.repository.StudySetRepository
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
class AddStudySetToFolderViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    private val studySetRepository: StudySetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddStudySetToFolderUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<AddStudySetToFolderUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val folderId: String = savedStateHandle["folderId"] ?: ""
        Timber.d("AddStudySetViewModel: $folderId")
        _uiState.update { it.copy(folderId = folderId) }
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: return@launch
            val ownerId = appManager.userId.firstOrNull() ?: return@launch
            val userAvatar = appManager.userAvatarUrl.firstOrNull() ?: return@launch
            val username = appManager.username.firstOrNull() ?: return@launch
            _uiState.update {
                it.copy(
                    token = token,
                    userId = ownerId,
                    userAvatar = userAvatar,
                    username = username
                )
            }
            getStudySets()
        }
    }

    fun onEvent(event: AddStudySetToFolderUiAction) {
        when (event) {
            AddStudySetToFolderUiAction.AddStudySetToFolder -> {
                doneClick()
            }

            is AddStudySetToFolderUiAction.ToggleStudySetImport -> {
                toggleStudySetImport(event.studySetId)
            }
        }
    }

    private fun getStudySets() {
        viewModelScope.launch {
            studySetRepository.getStudySetsByOwnerId(
                _uiState.value.token,
                _uiState.value.userId,
                null,
                _uiState.value.folderId
            )
                .collectLatest { resources ->
                    when (resources) {
                        is Resources.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    studySets = resources.data ?: emptyList(),
                                    studySetImportedIds = resources.data?.filter { studySetResponseModel -> studySetResponseModel.isImported == true }
                                        ?.map { setResponseModel -> setResponseModel.id }
                                        ?: emptyList()
                                )
                            }
                        }

                        is Resources.Error -> {
                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                            _uiEvent.send(
                                AddStudySetToFolderUiEvent.Error(
                                    resources.message ?: "An error occurred"
                                )
                            )
                        }

                        is Resources.Loading -> {
                            _uiState.update {
                                it.copy(isLoading = true)
                            }
                        }
                    }
                }
        }
    }

    private fun doneClick() {
        viewModelScope.launch {
            val addStudySetToFolderRequestModel = AddStudySetToFolderRequestModel(
                folderId = _uiState.value.folderId,
                studySetIds = _uiState.value.studySetImportedIds
            )
            studySetRepository.addStudySetToFolder(
                token = tokenManager.accessToken.firstOrNull() ?: "",
                addStudySetToFolderRequestModel
            ).collectLatest { resources ->
                when (resources) {
                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                        _uiEvent.send(AddStudySetToFolderUiEvent.StudySetAddedToFolder)
                    }

                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                        _uiEvent.send(
                            AddStudySetToFolderUiEvent.Error(
                                resources.message ?: "An error occurred"
                            )
                        )
                    }

                    is Resources.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun toggleStudySetImport(studySetId: String) {
        val studySetImportedIds = _uiState.value.studySetImportedIds.toMutableList()
        if (studySetImportedIds.contains(studySetId)) {
            studySetImportedIds.remove(studySetId)
        } else {
            studySetImportedIds.add(studySetId)
        }
        _uiState.update {
            it.copy(studySetImportedIds = studySetImportedIds)
        }
    }
}
