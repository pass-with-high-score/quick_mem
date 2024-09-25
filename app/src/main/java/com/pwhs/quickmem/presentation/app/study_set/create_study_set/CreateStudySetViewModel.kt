package com.pwhs.quickmem.presentation.app.study_set.create_study_set

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateStudySetViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(CreateStudySetUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<CreateStudySetUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CreateStudySetUiAction) {
        when (event) {
            is CreateStudySetUiAction.ColorChanged -> {
                _uiState.update {
                    it.copy(colorModel = event.colorModel)
                }
            }

            is CreateStudySetUiAction.PublicChanged -> {
                _uiState.update {
                    it.copy(isPublic = event.isPublic)
                }
            }

            is CreateStudySetUiAction.SaveClicked -> {
                _uiEvent.trySend(CreateStudySetUiEvent.SaveClicked)
            }

            is CreateStudySetUiAction.SubjectChanged -> {
                _uiState.update {
                    it.copy(subjectModel = event.subjectModel)
                }
            }

            is CreateStudySetUiAction.NameChanged -> {
                _uiState.update {
                    it.copy(name = event.name)
                }
            }
        }
    }
}