package com.pwhs.quickmem.presentation.app.profile.choose_picture

sealed class ChoosePictureUiAction {
    data class ImageSelected(val imgUrl: String) : ChoosePictureUiAction()
}