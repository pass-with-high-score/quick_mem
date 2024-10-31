package com.pwhs.quickmem.presentation.app.folder.edit

data class EditFolderUiState(
    val id: String = "",
    val title: String = "",
    val titleError: String = "",
    val description: String = "",
    val isPublic: Boolean = false,
    val isLoading: Boolean = false,
)