package com.pwhs.quickmem.presentation.app.classes.add_folder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.classes.AddFoldersToClassRequestModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.repository.ClassRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFolderToClassViewModel @Inject constructor(
    private val classRepository: ClassRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _folders = MutableStateFlow<List<GetFolderResponseModel>>(emptyList())
    val folders: StateFlow<List<GetFolderResponseModel>> = _folders

    private val _uiState = MutableStateFlow(AddFolderToClassUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<AddFolderToClassUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val classId: String = savedStateHandle["classId"] ?: ""
        _uiState.update {
            it.copy(
                classId = classId
            )
        }
    }

    fun onEvent(event: AddFolderToClassUIAction) {
        when (event) {
            AddFolderToClassUIAction.Refresh -> {
                TODO()
            }

            AddFolderToClassUIAction.RefreshFolders -> {
                TODO()
            }

            AddFolderToClassUIAction.SaveClicked -> {
                addFolderToClass()
            }
        }
    }

    private fun addFolderToClass() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            classRepository.addFoldersToClass(
                token, AddFoldersToClassRequestModel(
                    userId = userId,
                    classId = _uiState.value.classId,
                    folderIds = listOf(

                    )
                )
            ).collectLatest { resources ->
                when (resources) {
                    is Resources.Error -> {
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
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }
}