package com.pwhs.quickmem.presentation.app.profile.choose_picture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.BuildConfig
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChoosePictureViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChoosePictureUiState())
    val uiState: StateFlow<ChoosePictureUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ChoosePictureUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getAvatars()
    }

    private fun getAvatars() {
        viewModelScope.launch {
            val avatarUrls = mutableListOf<String>()
            for (index in 1..18) {
                val imageUrl = "${BuildConfig.BASE_URL}public/images/avatar/$index.jpg"
                avatarUrls.add(imageUrl)
            }
            _uiState.update { it.copy(avatarUrls = avatarUrls) }
        }
    }
}