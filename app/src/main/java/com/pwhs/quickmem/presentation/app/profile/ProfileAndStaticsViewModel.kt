package com.pwhs.quickmem.presentation.app.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileAndStaticsViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileAndStaticsUiState())
    val uiState: StateFlow<ProfileAndStaticsUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ProfileAndStaticsUiEventUiEvent>()
    val uiEvent: SharedFlow<ProfileAndStaticsUiEventUiEvent> = _uiEvent

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val fetchedData = "User Profile Data"
                _uiState.value = ProfileAndStaticsUiState(profileData = fetchedData)

                _uiEvent.emit(ProfileAndStaticsUiEventUiEvent.LoadProfile)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load profile data"
                )
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}
