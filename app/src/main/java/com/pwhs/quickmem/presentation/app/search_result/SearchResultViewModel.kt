package com.pwhs.quickmem.presentation.app.search_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.domain.repository.ClassRepository
import com.pwhs.quickmem.domain.repository.FolderRepository
import com.pwhs.quickmem.domain.repository.StudySetRepository
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultCreatorEnum
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultSizeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
class SearchResultViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val studySetRepository: StudySetRepository,
    private val classRepository: ClassRepository,
    private val folderRepository: FolderRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchResultUiState())
    val uiState = _uiState.asStateFlow()

    private val _studySetState: MutableStateFlow<PagingData<GetStudySetResponseModel>> =
        MutableStateFlow(PagingData.empty())
    val studySetState: MutableStateFlow<PagingData<GetStudySetResponseModel>> = _studySetState

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
            getUsers()
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
                    getClasses()
                }
            }

            SearchResultUiAction.RefreshFolders -> {
                viewModelScope.launch {
                    delay(500)
                    getFolders()
                }
            }

            SearchResultUiAction.RefreshStudySets -> {
                viewModelScope.launch {
                    Timber.d("Refresh study sets")
                    delay(500)
                    getStudySets()
                }
            }

            is SearchResultUiAction.ColorChanged -> {
                _uiState.update {
                    it.copy(colorModel = event.colorModel)
                }
            }

            is SearchResultUiAction.SubjectChanged -> {
                _uiState.update {
                    it.copy(subjectModel = event.subjectModel)
                }
            }

            SearchResultUiAction.ApplyFilter -> {
                getStudySets()
            }

            is SearchResultUiAction.CreatorTypeChanged -> {
                _uiState.update {
                    it.copy(creatorTypeModel = event.creatorType)
                }
            }

            is SearchResultUiAction.SizeChanged -> {
                _uiState.update {
                    it.copy(sizeStudySetModel = event.sizeModel)
                }
            }

            SearchResultUiAction.ResetFilter -> {
                _uiState.update {
                    it.copy(
                        colorModel = ColorModel.defaultColors.first(),
                        subjectModel = SubjectModel.defaultSubjects.first(),
                        sizeStudySetModel = SearchResultSizeEnum.ALL,
                        creatorTypeModel = SearchResultCreatorEnum.ALL
                    )
                }
                getStudySets()
            }
        }
    }

    private fun getStudySets() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            studySetRepository.getSearchResultStudySets(
                token = _uiState.value.token,
                title = _uiState.value.query,
                size = _uiState.value.sizeStudySetModel,
                creatorType = _uiState.value.creatorTypeModel,
                page = 1,
                colorId = _uiState.value.colorModel.id,
                subjectId = _uiState.value.subjectModel.id
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
        }
    }

    private fun getClasses() {
        viewModelScope.launch {
            classRepository.getSearchResultClasses(
                token = _uiState.value.token,
                title = _uiState.value.query,
                page = 1
            ).collectLatest { resources ->
                when (resources) {
                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                classes = resources.data ?: emptyList(),
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

    private fun getFolders() {
        viewModelScope.launch {
            folderRepository.getSearchResultFolders(
                token = _uiState.value.token,
                title = _uiState.value.query,
                page = 1
            ).collectLatest { resources ->
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

    private fun getUsers() {
        viewModelScope.launch {
            authRepository.searchUser(
                token = _uiState.value.token,
                username = _uiState.value.query,
                page = 1
            ).collectLatest { resources ->
                when (resources) {
                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                users = resources.data ?: emptyList(),
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
}