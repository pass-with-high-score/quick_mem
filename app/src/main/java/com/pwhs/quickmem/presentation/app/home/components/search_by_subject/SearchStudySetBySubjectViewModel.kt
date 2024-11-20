package com.pwhs.quickmem.presentation.app.home.components.search_by_subject

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.domain.repository.StudySetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchStudySetBySubjectViewModel @Inject constructor(
    private val studySetRepository: StudySetRepository,
    private val tokenManager: TokenManager,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchStudySetBySubjectUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<SearchStudySetBySubjectUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val id = savedStateHandle.get<Int>("id") ?: 0
        _uiState.update { it.copy(id = id) }
    }

    fun onEvent(event: SearchStudySetBySubjectUiAction) {
        when (event) {
            SearchStudySetBySubjectUiAction.RefreshStudySets -> {
                viewModelScope.launch {
                    Timber.d("Refresh study sets")
                    delay(500)
                    searchStudySetBySubject()
                }
            }
        }
    }

    private fun searchStudySetBySubject() {
        viewModelScope.launch {

        }
    }
}