package com.pwhs.quickmem.presentation.app.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appManager: AppManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadProfile()
    }

    fun onEvent(event: ProfileUiAction) {
        when (event) {
            ProfileUiAction.LoadProfile -> loadProfile()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val username = appManager.userName.firstOrNull() ?: ""
            val avatar = appManager.userAvatar.firstOrNull() ?: ""
            Timber.d("Loaded profile: $username, $avatar")
            _uiState.value = _uiState.value.copy(
                username = username,
                userAvatar = avatar
            )
        }
    }

    fun getUserData(): Pair<String, String> {
        return Pair(_uiState.value.username, _uiState.value.userAvatar)
    }


    // TODO: Load data from server
}
