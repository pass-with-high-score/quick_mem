package com.pwhs.quickmem.presentation.app.classes.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.classes.DeleteFolderRequestModel
import com.pwhs.quickmem.domain.model.classes.DeleteStudySetsRequestModel
import com.pwhs.quickmem.domain.model.classes.ExitClassRequestModel
import com.pwhs.quickmem.domain.model.classes.JoinClassRequestModel
import com.pwhs.quickmem.domain.model.classes.RemoveMembersRequestModel
import com.pwhs.quickmem.domain.model.classes.SaveRecentAccessClassRequestModel
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
        _uiState.update { it.copy(id = id) }
        getClassByID()
    }

    fun onEvent(event: ClassDetailUiAction) {
        when (event) {
            is ClassDetailUiAction.Refresh -> {
                getClassByID()
            }

            is ClassDetailUiAction.NavigateToWelcomeClicked -> {
                _uiEvent.trySend(ClassDetailUiEvent.NavigateToWelcome)
            }

            is ClassDetailUiAction.DeleteClass -> {
                deleteClass(id = _uiState.value.id)
                _uiEvent.trySend(ClassDetailUiEvent.ClassDeleted)
            }

            is ClassDetailUiAction.EditClass -> {
                _uiEvent.trySend(ClassDetailUiEvent.NavigateToEditClass)
            }

            is ClassDetailUiAction.OnNavigateToAddFolder -> {
                _uiEvent.trySend(ClassDetailUiEvent.OnNavigateToAddFolder)
            }

            is ClassDetailUiAction.OnNavigateToAddStudySets -> {
                _uiEvent.trySend(ClassDetailUiEvent.OnNavigateToAddStudySets)
            }

            is ClassDetailUiAction.ExitClass -> {
                exitClass()
            }

            is ClassDetailUiAction.NavigateToRemoveMembers -> {
                _uiEvent.trySend(ClassDetailUiEvent.OnNavigateToRemoveMembers)
            }

            is ClassDetailUiAction.OnDeleteMember -> {
                removeMember(event.memberId)
            }

            ClassDetailUiAction.OnJoinClass -> {
                joinClassByToken()
            }

            is ClassDetailUiAction.OnDeleteStudySetInClass -> {
                deleteStudySetInClass(event.studySetId)
            }

            is ClassDetailUiAction.OnDeleteFolderInClass -> {
                deleteFolderInClass(event.folderId)
            }
        }
    }

    private fun getClassByID() {
        val id = _uiState.value.id
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            classRepository.getClassById(token, id).collectLatest { resource ->
                when (resource) {
                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                    }

                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        resource.data?.let { data ->
                            val isOwner = data.owner.id == appManager.userId.firstOrNull()
                            val isMember =
                                data.members?.any { it.id == appManager.userId.firstOrNull() }
                                    ?: false
                            _uiState.update {
                                it.copy(
                                    title = data.title,
                                    description = data.description,
                                    joinClassCode = data.joinToken,
                                    id = data.id,
                                    isLoading = false,
                                    isMember = isMember,
                                    isOwner = isOwner,
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
                        saveRecentAccessClass()
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

    private fun exitClass() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            val classId = _uiState.value.id
            classRepository.exitClass(token, ExitClassRequestModel(userId, classId))
                .collectLatest { resource ->
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
                                Timber.d("Exited this class")
                                _uiState.update {
                                    it.copy(isLoading = false)
                                }
                                _uiEvent.send(ClassDetailUiEvent.ExitClass)
                            }
                        }
                    }
                }
        }
    }

    private fun removeMember(memberId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val classId = _uiState.value.id
            val userId = appManager.userId.firstOrNull() ?: ""
            classRepository.removeMembers(
                token, RemoveMembersRequestModel(
                    userId = userId,
                    classId = classId,
                    memberIds = listOf(memberId)
                )
            ).collectLatest { resource ->
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
                        val members = _uiState.value.members.toMutableList()
                        members.removeAll { it.id == memberId }
                        resource.data?.let {
                            Timber.d("Member removed")
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    members = members
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun joinClassByToken() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            val classId = _uiState.value.id
            val joinClassCode = _uiState.value.joinClassCode
            classRepository.joinClass(token, JoinClassRequestModel(joinClassCode, userId, classId))
                .collectLatest { resource ->
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
                            getClassByID()
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isMember = true
                                )
                            }
                            _uiEvent.trySend(ClassDetailUiEvent.OnJoinClass)
                        }
                    }
                }
        }
    }


    private fun deleteStudySetInClass(studySetId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            val classId = _uiState.value.id
            classRepository.deleteStudySetInClass(
                token,
                DeleteStudySetsRequestModel(userId, classId, studySetId)
            ).collectLatest { resource ->
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

                    is Resources.Success ->
                        resource.data?.let {
                            val studySets = _uiState.value.studySets.toMutableList()
                            studySets.removeAll { it.id == studySetId }
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    studySets = studySets
                                )
                            }
                        }
                }
            }
        }
    }

    private fun deleteFolderInClass(folderId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            val classId = _uiState.value.id
            classRepository.deleteFolderInClass(
                token,
                DeleteFolderRequestModel(userId, classId, folderId)
            ).collectLatest { resource ->
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

                    is Resources.Success ->
                        resource.data?.let {
                            val folders = _uiState.value.folders.toMutableList()
                            folders.removeAll { it.id == folderId }
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    folders = folders
                                )
                            }
                        }
                }
            }
        }
    }

    private fun saveRecentAccessClass() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            val classId = _uiState.value.id
            val saveRecentAccessClassRequestModel =
                SaveRecentAccessClassRequestModel(userId, classId)
            classRepository.saveRecentAccessClass(token, saveRecentAccessClassRequestModel)
                .collectLatest { resource ->
                    when (resource) {
                        is Resources.Loading -> {
                            Timber.d("Loading")
                        }

                        is Resources.Success -> {
                            Timber.d("Success")
                        }

                        is Resources.Error -> {
                            Timber.d("Error")
                        }
                    }
                }
        }
    }
}
