package com.pwhs.quickmem.presentation.app.profile.change_avatar

data class ChangeAvatarUiState(
    val isLoading: Boolean = false,
    val avatarUrls: List<String> = emptyList(),
    val selectedAvatarUrl: String? = null,
    val avatarUrl: String? = null
)