package com.pwhs.quickmem.presentation.app.folder.create

data class CreateFolderUiState(
    val isLoading: Boolean = false,
    val title: String = "",
    val titleError: String = "",
    val description: String = "",
    val isPublic: Boolean = false,
)