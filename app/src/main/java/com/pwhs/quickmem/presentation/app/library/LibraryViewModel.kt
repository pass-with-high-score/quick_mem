package com.pwhs.quickmem.presentation.app.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.StudySetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val studySetRepository: StudySetRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LibraryUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getStudySets()
    }

    private fun getStudySets() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: return@launch
            val ownerId = appManager.userId.firstOrNull() ?: return@launch

            studySetRepository.getStudySetsByOwnerId(token, ownerId).collectLatest { resources ->
                when (resources) {
                    is Resources.Success -> {
                        _uiState.value =
                            resources.data?.let { _uiState.value.copy(studySets = it) }!!
                    }

                    is Resources.Error -> {
                        resources.message?.let { LibraryUiEvent.Error(it) }
                            ?.let { _uiEvent.send(it) }
                    }

                    is Resources.Loading -> {
                        _uiEvent.send(LibraryUiEvent.Loading)
                    }
                }
            }
        }
    }
}