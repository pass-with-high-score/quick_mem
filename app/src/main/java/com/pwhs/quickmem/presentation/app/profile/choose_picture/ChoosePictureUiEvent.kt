package com.pwhs.quickmem.presentation.app.profile.choose_picture


sealed class ChoosePictureUiEvent{
    data object ImageSelected : ChoosePictureUiEvent()
    data class Error(val message: String) : ChoosePictureUiEvent()
}