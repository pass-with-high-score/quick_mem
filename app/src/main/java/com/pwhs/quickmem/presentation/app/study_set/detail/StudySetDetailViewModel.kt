package com.pwhs.quickmem.presentation.app.study_set.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.StudySetRepository
import com.pwhs.quickmem.util.toColor
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
class StudySetDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val studySetRepository: StudySetRepository,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(StudySetDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<StudySetDetailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val id: String = savedStateHandle["id"] ?: ""
        Timber.d("StudySetDetailViewModel: $id")
        _uiState.update { it.copy(id = id) }
        getStudySetById(id)
    }

    private fun getStudySetById(id: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            studySetRepository.getStudySetById(token, id).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        Timber.d("Loading")
                    }

                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                title = resource.data!!.title,
                                description = resource.data.description,
                                color = resource.data.color!!.hexValue.toColor(),
                                subject = resource.data.subject!!,
                                flashCardCount = resource.data.flashCardCount,
                                flashCards = resource.data.flashcards,
                                isPublic = resource.data.isPublic,
                                user = resource.data.user,
                                createdAt = resource.data.createdAt,
                                updatedAt = resource.data.updatedAt
                            )
                        }
                    }

                    is Resources.Error -> {
                        Timber.d("Error")
                    }
                }

            }
        }
    }
}