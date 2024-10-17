package com.pwhs.quickmem.presentation.app.flashcard.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.FlashCardRepository
import com.pwhs.quickmem.domain.repository.UploadImageRepository
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
class CreateFlashCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val flashCardRepository: FlashCardRepository,
    private val uploadImageRepository: UploadImageRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    application: Application
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(CreateFlashCardUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<CreateFlashCardUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val studySetId: String = savedStateHandle["studySetId"] ?: ""
        _uiState.update { it.copy(studySetId = studySetId) }
    }

    fun onEvent(event: CreateFlashCardUiAction) {
        when (event) {
            is CreateFlashCardUiAction.FlashCardDefinitionChanged -> {
                _uiState.update { it.copy(definition = event.definition) }
            }

            is CreateFlashCardUiAction.FlashCardDefinitionImageChanged -> {
                _uiState.update { it.copy(definitionImageUri = event.definitionImageUri) }
            }

            is CreateFlashCardUiAction.FlashCardExplanationChanged -> {
                _uiState.update { it.copy(explanation = event.explanation) }
            }

            is CreateFlashCardUiAction.FlashCardHintChanged -> {
                _uiState.update { it.copy(hint = event.hint) }
            }

            is CreateFlashCardUiAction.FlashCardTermChanged -> {
                _uiState.update { it.copy(term = event.term) }
            }

            is CreateFlashCardUiAction.SaveFlashCardClicked -> {

            }

            is CreateFlashCardUiAction.StudySetIdChanged -> {
                _uiState.update { it.copy(studySetId = event.studySetId) }
            }

            is CreateFlashCardUiAction.ShowExplanationClicked -> {
                _uiState.update { it.copy(showExplanation = event.showExplanation) }
            }

            is CreateFlashCardUiAction.ShowHintClicked -> {
                _uiState.update {
                    it.copy(showHint = event.showHint)
                }
            }

            is CreateFlashCardUiAction.UploadImage -> {

                viewModelScope.launch {
                    val token = tokenManager.accessToken.firstOrNull() ?: ""
                    uploadImageRepository.uploadImage(token, event.imageUri)
                        .collect { resource ->
                            when (resource) {
                                is Resources.Success -> {
                                    _uiState.update { it.copy(definitionImageURL = resource.data!!.url) }
                                }

                                is Resources.Error -> {
                                    // do nothing
                                }

                                is Resources.Loading -> {
                                    // do nothing
                                }
                            }
                        }
                }
            }
        }
    }

    private fun saveFlashCard() {
    }
}