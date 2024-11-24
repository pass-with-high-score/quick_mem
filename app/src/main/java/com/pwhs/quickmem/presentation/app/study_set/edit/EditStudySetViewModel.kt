package com.pwhs.quickmem.presentation.app.study_set.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.study_set.UpdateStudySetRequestModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.repository.StudySetLocalRepository
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
import javax.inject.Inject

@HiltViewModel
class EditStudySetViewModel @Inject constructor(
    private val studySetRepository: StudySetRepository,
    private val studySetLocalRepository: StudySetLocalRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditStudySetUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<EditStudySetUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val studySetId: String = savedStateHandle["studySetId"] ?: ""
        val studySetTitle = savedStateHandle["studySetTitle"] ?: ""
        val studySetDescription = savedStateHandle["studySetDescription"] ?: ""
        val studySetSubjectId: Int = savedStateHandle["studySetSubjectId"] ?: 1
        val studySetColorId: Int = savedStateHandle["studySetColorId"] ?: 1
        val studySetIsPublic: Boolean = savedStateHandle["studySetIsPublic"] ?: false
        _uiState.update {
            it.copy(
                id = studySetId,
                title = studySetTitle,
                description = studySetDescription,
                subjectModel = SubjectModel.defaultSubjects.first { it.id == studySetSubjectId },
                colorModel = ColorModel.defaultColors.first { it.id == studySetColorId },
                isPublic = studySetIsPublic
            )
        }
    }

    fun onEvent(event: EditStudySetUiAction) {
        when (event) {
            is EditStudySetUiAction.ColorChanged -> {
                _uiState.update {
                    it.copy(colorModel = event.colorModel)
                }
            }

            is EditStudySetUiAction.PublicChanged -> {
                _uiState.update {
                    it.copy(isPublic = event.isPublic)
                }
            }

            is EditStudySetUiAction.SaveClicked -> {
                val uiState = _uiState.value
                if (uiState.title.isEmpty()) {
                    _uiState.update {
                        it.copy(titleError = "Title is required")
                    }
                    return
                } else {
                    _uiState.update {
                        it.copy(titleError = "")
                    }
                    saveStudySet()
                }
            }

            is EditStudySetUiAction.SubjectChanged -> {
                _uiState.update {
                    it.copy(subjectModel = event.subjectModel)
                }
            }

            is EditStudySetUiAction.TitleChanged -> {
                _uiState.update {
                    it.copy(title = event.title)
                }
            }

            is EditStudySetUiAction.DescriptionChanged -> {
                _uiState.update {
                    it.copy(description = event.description)
                }
            }
        }
    }

    private fun saveStudySet() {
        viewModelScope.launch {
            val ownerId = appManager.userId.firstOrNull { true } ?: ""
            val updateStudySetRequestModel = UpdateStudySetRequestModel(
                title = uiState.value.title,
                subjectId = uiState.value.subjectModel.id,
                colorId = uiState.value.colorModel.id,
                isPublic = uiState.value.isPublic,
                description = uiState.value.description,
                ownerId = ownerId
            )
            studySetRepository.updateStudySet(
                token = tokenManager.accessToken.firstOrNull { true } ?: "",
                studySetId = uiState.value.id,
                updateStudySetRequestModel
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
                        _uiEvent.send(EditStudySetUiEvent.StudySetEdited)

                        studySetLocalRepository.updateStudySet(
                            id = uiState.value.id,
                            title = uiState.value.title,
                            description = uiState.value.description,
                            colorHex = uiState.value.colorModel.hexValue,
                            subjectName = uiState.value.subjectModel.name,
                            isPublic = uiState.value.isPublic
                        )
                    }

                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                        _uiEvent.send(EditStudySetUiEvent.ShowError(resource.message!!))
                    }
                }
            }
        }
    }
}