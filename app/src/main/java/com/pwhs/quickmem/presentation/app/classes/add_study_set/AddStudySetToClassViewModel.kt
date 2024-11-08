package com.pwhs.quickmem.presentation.app.classes.add_study_set

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.study_set.AddStudySetToClassRequestModel
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
class AddStudySetToClassViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    private val studySetRepository: StudySetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddStudySetToClassUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<AddStudySetToClassUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val classId: String = savedStateHandle["classId"] ?: ""
        Timber.d("AddStudySetViewModel: $classId")
        _uiState.update { it.copy(classId = classId) }
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: return@launch
            val ownerId = appManager.userId.firstOrNull() ?: return@launch
            val userAvatar = appManager.userAvatar.firstOrNull() ?: return@launch
            val username = appManager.userName.firstOrNull() ?: return@launch
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

    fun onEvent(event: AddStudySetToClassUiAction) {
        when (event) {
            AddStudySetToClassUiAction.AddStudySetToClass -> {
                doneClick()
            }

            is AddStudySetToClassUiAction.ToggleStudySetImport -> {
                Timber.d("Toggle Study Set Import: ${event.studySetId}")
                toggleStudySetImport(event.studySetId)
            }
        }
    }

    private fun getStudySets() {
        viewModelScope.launch {
            studySetRepository.getStudySetsByOwnerId(
                _uiState.value.token,
                _uiState.value.userId,
                _uiState.value.classId
            )
                .collectLatest { resources ->
                    when (resources) {
                        is Resources.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    studySets = resources.data ?: emptyList(),
                                    studySetImportedIds = resources.data?.filter { it.isImported == true }
                                        ?.map { it.id } ?: emptyList()
                                )
                            }
                        }

                        is Resources.Error -> {
                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                            _uiEvent.send(
                                AddStudySetToClassUiEvent.Error(
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
            val addStudySetToClassRequestModel = AddStudySetToClassRequestModel(
                userId = _uiState.value.userId,
                classId = _uiState.value.classId,
                studySetIds = _uiState.value.studySetImportedIds
            )
            studySetRepository.addStudySetToClass(
                token = tokenManager.accessToken.firstOrNull() ?: "",
                addStudySetToClassRequestModel
            ).collectLatest { resources ->
                when (resources) {
                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                        _uiEvent.send(AddStudySetToClassUiEvent.StudySetAddedToClass)
                    }

                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                        _uiEvent.send(
                            AddStudySetToClassUiEvent.Error(
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
