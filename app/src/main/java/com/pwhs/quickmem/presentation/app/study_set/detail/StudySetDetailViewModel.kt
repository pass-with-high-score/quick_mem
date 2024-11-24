package com.pwhs.quickmem.presentation.app.study_set.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.data.enums.ResetType
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.study_set.StudySetModel
import com.pwhs.quickmem.domain.repository.FlashCardRepository
import com.pwhs.quickmem.domain.repository.StudySetLocalRepository
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
    private val studySetLocalRepository: StudySetLocalRepository,
    private val studySetRepository: StudySetRepository,
    private val flashCardRepository: FlashCardRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(StudySetDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<StudySetDetailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val id: String = savedStateHandle["id"] ?: ""
        _uiState.update { it.copy(id = id) }
        getStudySetById()
    }

    fun onEvent(event: StudySetDetailUiAction) {
        when (event) {
            is StudySetDetailUiAction.Refresh -> {
                getStudySetById()
            }

            is StudySetDetailUiAction.OnIdOfFlashCardSelectedChanged -> {
                _uiState.update { it.copy(idOfFlashCardSelected = event.id) }
            }

            StudySetDetailUiAction.OnDeleteFlashCardClicked -> {
                deleteFlashCard()
            }

            is StudySetDetailUiAction.OnStarFlashCardClicked -> {
                toggleStarredFlashCard(event.id, event.isStarred)
            }

            StudySetDetailUiAction.OnEditStudySetClicked -> {
                _uiEvent.trySend(StudySetDetailUiEvent.NavigateToEditStudySet)
            }

            StudySetDetailUiAction.OnEditFlashCardClicked -> {
                _uiEvent.trySend(StudySetDetailUiEvent.NavigateToEditFlashCard)
            }

            StudySetDetailUiAction.OnDeleteStudySetClicked -> {
                deleteStudySet()
            }

            is StudySetDetailUiAction.OnResetProgressClicked -> {
                resetProgress(event.id)
            }

            StudySetDetailUiAction.OnMakeCopyClicked -> {
                makeCopyStudySet()
            }
        }
    }

    private fun getStudySetById() {
        val id = _uiState.value.id
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            studySetRepository.getStudySetById(token, id).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        val isOwner = appManager.userId.firstOrNull() == resource.data!!.owner.id
                        _uiState.update {
                            it.copy(
                                title = resource.data.title,
                                description = resource.data.description ?: "",
                                color = resource.data.color!!.hexValue.toColor(),
                                subject = resource.data.subject!!,
                                flashCardCount = resource.data.flashcardCount,
                                flashCards = resource.data.flashcards,
                                isPublic = resource.data.isPublic,
                                user = resource.data.owner,
                                createdAt = resource.data.createdAt,
                                updatedAt = resource.data.updatedAt,
                                colorModel = resource.data.color,
                                linkShareCode = resource.data.linkShareCode ?: "",
                                isLoading = false,
                                isOwner = isOwner,
                            )
                        }
                        val data = StudySetModel(
                            id = resource.data.id,
                            title = resource.data.title,
                            description = resource.data.description ?: "",
                            flashcardCount = resource.data.flashcardCount,
                            colorHex = resource.data.color!!.hexValue,
                            subjectName = resource.data.subject!!.name,
                            ownerId = resource.data.owner.id,
                            ownerUsername = resource.data.owner.username,
                            ownerAvatarUrl = resource.data.owner.avatarUrl,
                            isPublic = resource.data.isPublic
                        )
                        studySetLocalRepository.addStudySet(data)
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
                            Timber.d(resource.message)
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
                            _uiState.update { it.copy(isLoading = true) }
                        }

                        is Resources.Success -> {
                            _uiEvent.send(StudySetDetailUiEvent.StudySetDeleted)

                            studySetLocalRepository.deleteStudySet(_uiState.value.id)
                        }

                        is Resources.Error -> {
                            Timber.d(resource.message)
                            _uiState.update { it.copy(isLoading = false) }
                        }
                    }
                }
        }
    }

    private fun resetProgress(id: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            studySetRepository.resetProgress(token, id, resetType = ResetType.RESET_ALL.type)
                .collect { resource ->
                    when (resource) {
                        is Resources.Loading -> {
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

    private fun makeCopyStudySet() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            val studySetId = _uiState.value.id
            studySetRepository.makeCopyStudySet(token, studySetId, userId).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _uiEvent.send(StudySetDetailUiEvent.StudySetCopied(resource.data?.id ?: ""))
                    }

                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }
}