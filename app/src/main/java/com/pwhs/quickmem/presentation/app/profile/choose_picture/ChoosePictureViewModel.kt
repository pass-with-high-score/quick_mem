package com.pwhs.quickmem.presentation.app.profile.choose_picture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChoosePictureViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){
    init {

    }

    private fun getAvatar(avatarId:Int) {
        viewModelScope.launch {

        }
    }
}