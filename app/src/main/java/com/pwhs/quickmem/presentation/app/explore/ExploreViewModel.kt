package com.pwhs.quickmem.presentation.app.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.StreakRepository
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
class ExploreViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val streakRepository: StreakRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ExploreUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getTopStreaks()
    }

    fun onEvent(event: ExploreUiAction) {
        when (event) {
            ExploreUiAction.RefreshTopStreaks -> {
                getTopStreaks()
            }
        }
    }

    private fun getTopStreaks() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            streakRepository.getTopStreaks(token, null).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                topStreaks = resource.data ?: emptyList()
                            )
                        }
                    }

                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _uiEvent.send(ExploreUiEvent.Error(resource.message ?: ""))
                    }
                }
            }
        }
    }
}