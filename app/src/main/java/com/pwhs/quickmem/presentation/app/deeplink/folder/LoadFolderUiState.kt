package com.pwhs.quickmem.presentation.app.deeplink.folder

data class LoadFolderUiState(
    val folderCode: String = "",
    val isLoading: Boolean = true,
    val folderId: String? = null
)