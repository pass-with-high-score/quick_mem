package com.pwhs.quickmem.presentation.app.profile.change_avatar


sealed class ChangeAvatarUiEvent{
    data class AvatarUpdated(val avatarUrl: String) : ChangeAvatarUiEvent()
    data class Error(val message: String) : ChangeAvatarUiEvent()
}