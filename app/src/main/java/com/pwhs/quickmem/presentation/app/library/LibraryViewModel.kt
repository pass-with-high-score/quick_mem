package com.pwhs.quickmem.presentation.app.library

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
class LibraryViewModel @Inject constructor(
    private val studySetRepository: StudySetRepository,
    private val classRepository: ClassRepository,
    private val folderRepository: FolderRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LibraryUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
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

    fun onEvent(event: LibraryUiAction) {
        when (event) {
            is LibraryUiAction.Refresh -> {
                getStudySets()
                getClasses()
                getFolders()
            }

            LibraryUiAction.RefreshStudySets -> {
                viewModelScope.launch {
                    delay(500)
                    getStudySets()
                }
            }

            LibraryUiAction.RefreshClasses -> {
                viewModelScope.launch {
                    delay(500)
                    getClasses()
                }
            }

            LibraryUiAction.RefreshFolders -> {
                viewModelScope.launch {
                    delay(500)
                    getFolders()
                }
            }
        }
    }

    private fun getStudySets() {
        viewModelScope.launch {
            studySetRepository.getStudySetsByOwnerId(_uiState.value.token, _uiState.value.userId, null)
                .collectLatest { resources ->
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
                                LibraryUiEvent.Error(
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
            classRepository.getClassByOwnerId(_uiState.value.token, _uiState.value.userId)
                .collectLatest { resource ->
                    when (resource) {
                        is Resources.Error -> {
                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                            _uiEvent.send(
                                LibraryUiEvent.Error(
                                    resource.message ?: "An error occurred"
                                )
                            )
                        }

                        is Resources.Loading -> {
                            _uiState.update {
                                it.copy(isLoading = true)
                            }
                        }

                        is Resources.Success -> {
                            _uiState.update {
                                it.copy(
                                    classes = resource.data ?: emptyList(),
                                    isLoading = false,
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun getFolders() {
        viewModelScope.launch {
            folderRepository.getFoldersByUserId(_uiState.value.token, _uiState.value.userId)
                .collectLatest { resources ->
                    when (resources) {
                        is Resources.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    folders = resources.data ?: emptyList(),
                                )
                            }
                        }

                        is Resources.Error -> {
                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                            _uiEvent.send(
                                LibraryUiEvent.Error(
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
}