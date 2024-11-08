package com.pwhs.quickmem.presentation.app.classes.add_folder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.repository.FolderRepository
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddFolderToClassViewModel @Inject constructor(
    private val folderRepository: FolderRepository,
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
        Timber.d("AddFolderToClassViewModel: $classId")
        _uiState.update { it.copy(classId = classId) }
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
            getFolders()
        }
    }

    fun onEvent(event: AddFolderToClassUIAction) {
        when (event) {
            AddFolderToClassUIAction.AddStudySetToClass -> {

            }

            is AddFolderToClassUIAction.ToggleStudySetImport -> {

            }
        }
    }

    private fun getFolders() {
        viewModelScope.launch {
            folderRepository.getFoldersByUserId(
                _uiState.value.token,
                _uiState.value.userId,
                _uiState.value.classId
            ).collectLatest { resources ->
                when (resources) {
                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                folders = resources.data ?: emptyList(),
                                folderImportedIds = resources.data?.filter { it.isImported == true }
                                    ?.map { it.id } ?: emptyList()
                            )
                        }
                        Timber.d("Folders: ${resources.data}")
                    }

                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                        _uiEvent.send(
                            AddFolderToClassUIEvent.ShowError(
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