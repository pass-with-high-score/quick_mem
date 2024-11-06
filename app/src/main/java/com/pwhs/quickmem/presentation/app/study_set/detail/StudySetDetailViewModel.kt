package com.pwhs.quickmem.presentation.app.study_set.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.data.ResetType
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.FlashCardRepository
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
    private val flashCardRepository: FlashCardRepository,
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

    fun onEvent(event: StudySetDetailUiAction) {
        when (event) {
            is StudySetDetailUiAction.Refresh -> {
                getStudySetById(_uiState.value.id)
            }

            is StudySetDetailUiAction.OnIdOfFlashCardSelectedChanged -> {
                Timber.d("OnIdOfFlashCardSelectedChanged: ${event.id}")
                _uiState.update { it.copy(idOfFlashCardSelected = event.id) }
            }

            StudySetDetailUiAction.OnDeleteFlashCardClicked -> {
                Timber.d("OnDeleteFlashCardClicked")
                deleteFlashCard()
            }

            is StudySetDetailUiAction.OnStarFlashCardClicked -> {
                Timber.d("OnStarFlashCardClicked: ${event.isStarred}")
                toggleStarredFlashCard(event.id, event.isStarred)
            }

            StudySetDetailUiAction.OnEditStudySetClicked -> {
                Timber.d("OnNavigateToEditFlashCardClicked")
                _uiEvent.trySend(StudySetDetailUiEvent.NavigateToEditStudySet)
            }

            StudySetDetailUiAction.OnEditFlashCardClicked -> {
                Timber.d("OnNavigateToEditFlashCardClicked")
                _uiEvent.trySend(StudySetDetailUiEvent.NavigateToEditFlashCard)
            }

            StudySetDetailUiAction.OnDeleteStudySetClicked -> {
                Timber.d("OnDeleteStudySetClicked")
                deleteStudySet()
            }

            is StudySetDetailUiAction.OnResetProgressClicked -> {
                Timber.d("OnResetProgressClicked: ${event.id}")
                resetProgress(event.id)
            }
        }
    }

    private fun getStudySetById(id: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            studySetRepository.getStudySetById(token, id).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        Timber.d("Loading")
                        _uiState.update { it.copy(isLoading = true) }
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
                                user = resource.data.owner,
                                createdAt = resource.data.createdAt,
                                updatedAt = resource.data.updatedAt,
                                colorModel = resource.data.color,
                                linkShareCode = resource.data.linkShareCode ?: "",
                                isLoading = false,
                            )
                        }
                    }

                    is Resources.Error -> {
                        Timber.d("Error")
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }

            }
        }
    }

    private fun deleteFlashCard() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            flashCardRepository.deleteFlashCard(token, _uiState.value.idOfFlashCardSelected)
                .collect { resource ->
                    when (resource) {
                        is Resources.Loading -> {
                            Timber.d("Loading")
                        }

                        is Resources.Success -> {
                            _uiEvent.send(StudySetDetailUiEvent.FlashCardDeleted)
                        }

                        is Resources.Error -> {
                            Timber.d("Error")
                        }
                    }
                }
        }
    }

    private fun toggleStarredFlashCard(id: String, isStarred: Boolean) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            flashCardRepository.toggleStarredFlashCard(
                token,
                id,
                isStarred
            )
                .collect { resource ->
                    when (resource) {
                        is Resources.Loading -> {
                            Timber.d("Loading")
                        }

                        is Resources.Success -> {
                            Timber.d(resource.data?.message)
                            _uiEvent.send(StudySetDetailUiEvent.FlashCardStarred)
                        }

                        is Resources.Error -> {
                            Timber.d("Error")
                        }
                    }
                }
        }
    }

    private fun deleteStudySet() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            studySetRepository.deleteStudySet(token, _uiState.value.id)
                .collect { resource ->
                    when (resource) {
                        is Resources.Loading -> {
                            Timber.d("Loading")
                        }

                        is Resources.Success -> {
                            _uiEvent.send(StudySetDetailUiEvent.StudySetDeleted)
                        }

                        is Resources.Error -> {
                            Timber.d("Error")
                        }
                    }
                }
        }
    }

    private fun resetProgress(id: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            studySetRepository.resetProgress(token, id, resetType = ResetType.resetAll.name)
                .collect { resource ->
                    when (resource) {
                        is Resources.Loading -> {
                            Timber.d("Loading")
                            _uiState.update { it.copy(isLoading = true) }
                        }

                        is Resources.Success -> {
                            _uiState.update { it.copy(isLoading = false) }
                            _uiEvent.send(StudySetDetailUiEvent.StudySetProgressReset)
                        }

                        is Resources.Error -> {
                            Timber.d("Error")
                            _uiState.update { it.copy(isLoading = false) }
                        }
                    }
                }
        }
    }
}