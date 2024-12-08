package com.pwhs.quickmem.presentation.app.profile.change_avatar

sealed class ChangeAvatarUiAction {
    data class ImageSelected(val avatarUrl: String) : ChangeAvatarUiAction()
    data object SaveClicked : ChangeAvatarUiAction()
}