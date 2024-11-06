package com.pwhs.quickmem.presentation.app.classes.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.ClassRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ClassDetailViewModel @Inject constructor(
    private val classRepository: ClassRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(ClassDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ClassDetailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val joinClassCode: String = savedStateHandle["code"] ?: ""
        val id: String = savedStateHandle["id"] ?: ""
        val title: String = savedStateHandle["title"] ?: ""
        val description: String = savedStateHandle["description"] ?: ""
        viewModelScope.launch {
            appManager.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    _uiState.update {
                        it.copy(
                            isLogin = true,
                            joinClassCode = joinClassCode,
                            id = id,
                            title = title,
                            description = description
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLogin = false) }
                    onEvent(ClassDetailUiAction.NavigateToWelcomeClicked)
                }
            }
        }
        Timber.d("ClassDetailViewModel: $id")
        _uiState.update { it.copy(id = id) }
        getClassByID(id)
    }

    fun onEvent(event: ClassDetailUiAction) {
        when (event) {
            is ClassDetailUiAction.Refresh -> {
                getClassByID(id = _uiState.value.id)
            }

            ClassDetailUiAction.JoinClassClicked -> {
                TODO()
            }

            ClassDetailUiAction.NavigateToWelcomeClicked -> {
                _uiEvent.trySend(ClassDetailUiEvent.NavigateToWelcome)
            }

            ClassDetailUiAction.DeleteClass -> {
                deleteClass(id = _uiState.value.id)
                _uiEvent.trySend(ClassDetailUiEvent.ClassDeleted)
            }

            ClassDetailUiAction.EditClass -> {
                _uiEvent.trySend(ClassDetailUiEvent.NavigateToEditClass)
            }

            ClassDetailUiAction.onNavigateToAddFolder -> {
                _uiEvent.trySend(ClassDetailUiEvent.onNavigateToAddFolder)
            }

            ClassDetailUiAction.onNavigateToAddMember -> {
                _uiEvent.trySend(ClassDetailUiEvent.onNavigateToAddMember)
            }

            ClassDetailUiAction.onNavigateToAddStudySets -> {
                _uiEvent.trySend(ClassDetailUiEvent.onNavigateToAddStudySets)
            }
        }
    }

    private fun getClassByID(id: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            classRepository.getClassByID(token, id).collectLatest { resource ->
                when (resource) {
                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                    }

                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        resource.data?.let { data ->
                            _uiState.update {
                                it.copy(
                                    title = data.title,
                                    description = data.description,
                                    joinClassCode = data.joinToken,
                                    id = data.id,
                                    isLoading = false,
                                    allowSet = data.allowSetManagement,
                                    allowMember = data.allowMemberManagement,
                                    userResponseModel = data.owner,
                                    folders = data.folders ?: emptyList(),
                                    studySets = data.studySets ?: emptyList(),
                                    members = data.members ?: emptyList()
                                )
                            }
                        } ?: run {
                            _uiEvent.send(ClassDetailUiEvent.ShowError("Class not found"))
                        }
                    }
                }
            }
        }
    }

    private fun deleteClass(id: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            classRepository.deleteClass(token, id).collectLatest { resource ->
                when (resource) {
                    is Resources.Error -> {
                        Timber.e(resource.message)
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resources.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resources.Success -> {
                        resource.data?.let {
                            Timber.d("Folder deleted")
                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                            _uiEvent.send(ClassDetailUiEvent.ClassDeleted)
                        }

                    }
                }
            }
        }
    }
}