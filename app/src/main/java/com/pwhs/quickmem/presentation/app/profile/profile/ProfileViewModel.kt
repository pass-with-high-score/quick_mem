package com.pwhs.quickmem.presentation.app.profile.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.presentation.app.profile.ProfileAndStaticsUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appManager: AppManager,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())

    private val _nameState = MutableStateFlow("")
    private val _avatarUrlState = MutableStateFlow("")


    val avatarUrlState: Flow<String> = appManager.userAvatar
    val nameState: Flow<String> = appManager.userName


    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        onEvent(ProfileAndStaticsUiAction.LoadProfile)
    }


    init {
        appManager.userName.onEach { name ->
            _nameState.value = name
        }.launchIn(viewModelScope)
    }

    init {
        loadName()
        loadAvatar()
    }

    fun onEvent(event: ProfileAndStaticsUiAction) {
        when (event) {
            ProfileAndStaticsUiAction.LoadProfile -> loadProfile()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val userId = appManager.userId.firstOrNull() ?: return@launch
        }
    }

    private fun logout() {
        viewModelScope.launch {
            tokenManager.clearTokens()
        }
    }

    private fun loadName() {
        viewModelScope.launch {
            appManager.userName.collectLatest { name ->
                _nameState.value = name
            }
        }
    }

    private fun loadAvatar() {
        viewModelScope.launch {
            appManager.userAvatar.collectLatest { name ->
                _avatarUrlState.value = name
            }
        }
    }

    fun updateAvatar(newAvatarUrl: String) {
        viewModelScope.launch {
            appManager.saveUserAvatar(newAvatarUrl)
        }
    }
}