package com.pwhs.quickmem.presentation.app.folder.create

data class CreateFolderUiState(
    val title: String = "",
    val titleError: String = "",
    val description: String = "",
    val isPublic: Boolean = false,
)