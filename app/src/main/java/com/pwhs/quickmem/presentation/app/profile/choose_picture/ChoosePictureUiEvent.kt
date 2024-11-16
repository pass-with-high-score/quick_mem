package com.pwhs.quickmem.presentation.app.profile.choose_picture


sealed class ChoosePictureUiEvent{
    data class AvatarUpdated(val avatarUrl: String) : ChoosePictureUiEvent()
    data class Error(val message: String) : ChoosePictureUiEvent()
}