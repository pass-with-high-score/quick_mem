package com.pwhs.quickmem.presentation.app.profile.change_avatar

import android.net.Uri

sealed class ChangeAvatarUiAction {
    data class ImageSelected(val avatarUrl: String) : ChangeAvatarUiAction()
    data object SaveClicked : ChangeAvatarUiAction()
    data class OnImageUriChanged(val imageUri: Uri) : ChangeAvatarUiAction()
}