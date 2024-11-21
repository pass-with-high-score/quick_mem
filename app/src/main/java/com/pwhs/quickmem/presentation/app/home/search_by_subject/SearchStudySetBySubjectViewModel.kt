package com.pwhs.quickmem.presentation.app.home.search_by_subject

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.repository.StudySetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
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

    private val _studySetState: MutableStateFlow<PagingData<GetStudySetResponseModel>> =
        MutableStateFlow(PagingData.empty())
    val studySetState: MutableStateFlow<PagingData<GetStudySetResponseModel>> = _studySetState

    init {
        val id = savedStateHandle.get<Int>("id") ?: 0
        _uiState.update { it.copy(id = id) }
        val name = savedStateHandle.get<String>("name") ?: ""
        _uiState.update { it.copy(name = name) }

        searchStudySetBySubject()
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
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            try {
                _uiState.update { it.copy(isLoading = true) }
                studySetRepository.getStudySetBySubjectId(
                    token = token,
                    subjectId = _uiState.value.id,
                    page = 1
                ).distinctUntilChanged()
                    .onStart {
                        _studySetState.value = PagingData.empty()
                    }
                    .cachedIn(viewModelScope)
                    .onCompletion {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                    .collect { pagingData ->
                        _studySetState.value = pagingData
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _uiEvent.send(SearchStudySetBySubjectUiEvent.Error(e.message ?: "An error occurred"))
            }
        }
    }
}