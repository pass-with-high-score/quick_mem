package com.pwhs.quickmem.presentation.app.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState.asStateFlow()

    private val _avatarUrlState = MutableStateFlow("")
    val avatarUrlState: StateFlow<String> = _avatarUrlState.asStateFlow()

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
            appManager.userName.collectLatest { name ->
                _nameState.value = name
            }

            appManager.userAvatar.collect { avatarUrl ->
                Timber.d("Loaded avatar: $avatarUrl")
                _avatarUrlState.value = avatarUrl
            }
        }
    }

    fun updateAvatar(newAvatarUrl: String) {
        viewModelScope.launch {
            _avatarUrlState.value = newAvatarUrl
            appManager.saveUserAvatar(newAvatarUrl)
        }
    }
}
