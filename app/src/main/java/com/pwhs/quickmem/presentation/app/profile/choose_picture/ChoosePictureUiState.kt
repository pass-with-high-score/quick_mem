package com.pwhs.quickmem.presentation.app.profile.choose_picture

data class ChoosePictureUiState(
    val isLoading:Boolean = false,
    val avatarUrls: List<String> = emptyList(),
    val selectedAvatarUrl: String? = null
)