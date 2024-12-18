package com.pwhs.quickmem.presentation.app.profile.change_avatar

import com.pwhs.quickmem.domain.model.users.AvatarResponseModel

data class ChangeAvatarUiState(
    val isLoading: Boolean = false,
    val avatarUrls: List<AvatarResponseModel> = emptyList(),
    val selectedAvatarUrl: String? = null,
    val avatarUrl: String? = null
)