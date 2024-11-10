package com.pwhs.quickmem.presentation.app.study_set.add_to_class

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.domain.repository.ClassRepository
import com.pwhs.quickmem.domain.repository.StudySetRepository
import com.pwhs.quickmem.presentation.app.study_set.add_to_folder.AddStudySetToFoldersUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddStudySetToClassesViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    private val classRepository: ClassRepository,
    private val studySetRepository: StudySetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddStudySetToClassesUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<AddStudySetToClassesUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val studySetId: String = savedStateHandle["studySetId"] ?: ""
        Timber.d("AddStudySetViewModel: $studySetId")
        _uiState.update { it.copy(studySetId = studySetId) }
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
            getClasses()
        }
    }

    fun onEvent(event: AddStudySetToClassesUiAction) {
        when (event) {
            AddStudySetToClassesUiAction.AddStudySetToClasses -> {
                doneClick()
            }

            is AddStudySetToClassesUiAction.ToggleStudySetImport -> {
                Timber.d("Toggle Study Set Import: ${event.classId}")
                toggleFolderImport(event.classId)
            }
        }
    }

    private fun getClasses() {

    }

    private fun doneClick() {

    }

    private fun toggleFolderImport(classId: String) {
        val classImportedIds = _uiState.value.classImportedIds.toMutableList()
        if (classImportedIds.contains(classId)) {
            classImportedIds.remove(classId)
        } else {
            classImportedIds.add(classId)
        }
        _uiState.update {
            it.copy(classImportedIds = classImportedIds)
        }
    }
}
