package com.pwhs.quickmem.presentation.app.deeplink.study_set

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.StudySetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadStudySetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tokenManager: TokenManager,
    private val studySetRepository: StudySetRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoadStudySetUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoadStudySetUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val studySetCode = savedStateHandle.get<String>("studySetCode") ?: ""
        val type = savedStateHandle.get<String>("type") ?: ""

        _uiState.update {
            it.copy(
                studySetCode = studySetCode,
                type = type
            )
        }
        loadStudySet()
    }

    private fun loadStudySet() {
        viewModelScope.launch {
            val studySetCode = uiState.value.studySetCode
            val type = uiState.value.type
            val token = tokenManager.accessToken.firstOrNull()

            if (studySetCode.isEmpty() || type.isEmpty() || token == null) {
                _uiEvent.send(LoadStudySetUiEvent.UnAuthorized)
                return@launch
            }

            studySetRepository.getStudySetByCode(token, studySetCode).collect { resource ->
                when (resource) {
                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _uiEvent.send(LoadStudySetUiEvent.NotFound)
                    }

                    is Resources.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                studySetId = resource.data?.id
                            )
                        }
                        _uiEvent.send(LoadStudySetUiEvent.StudySetLoaded(resource.data?.id ?: ""))
                    }
                }
            }

        }
    }
}