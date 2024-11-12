package com.pwhs.quickmem.presentation.app.search_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.ClassRepository
import com.pwhs.quickmem.domain.repository.FolderRepository
import com.pwhs.quickmem.domain.repository.StudySetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val studySetRepository: StudySetRepository,
    private val classRepository: ClassRepository,
    private val folderRepository: FolderRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchResultUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<SearchResultUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val query = savedStateHandle.get<String>("query") ?: ""
        _uiState.update { it.copy(query = query) }

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
            getStudySets()
            getClasses()
            getFolders()
        }
    }

    fun onEvent(event: SearchResultUiAction) {
        when (event) {
            SearchResultUiAction.Refresh -> {
                getStudySets()
                getClasses()
                getFolders()
            }

            SearchResultUiAction.RefreshClasses -> {
                viewModelScope.launch {
                    delay(500)
                    getStudySets()
                }
            }

            SearchResultUiAction.RefreshFolders -> {
                viewModelScope.launch {
                    delay(500)
                    getClasses()
                }
            }

            SearchResultUiAction.RefreshStudySets -> {
                viewModelScope.launch {
                    delay(500)
                    getFolders()
                }
            }
        }
    }

    private fun getStudySets() {
        viewModelScope.launch {
            studySetRepository.getSearchResultStudySets(
                token = _uiState.value.token,
                query = _uiState.value.query,
                size = "all",
                creatorType = null,
                page = 1,
                colorId = null,
                subjectId = null
            ).collectLatest { resources ->
                when (resources) {
                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                studySets = resources.data ?: emptyList(),
                            )
                        }
                    }

                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                        _uiEvent.send(
                            SearchResultUiEvent.Error(
                                resources.message ?: "An error occurred"
                            )
                        )
                    }

                    is Resources.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun getClasses() {
        viewModelScope.launch {

        }
    }

    private fun getFolders() {
        viewModelScope.launch {

        }
    }
}