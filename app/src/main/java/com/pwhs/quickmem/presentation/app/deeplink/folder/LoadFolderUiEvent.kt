package com.pwhs.quickmem.presentation.app.deeplink.folder


sealed class LoadFolderUiEvent {
    data class FolderLoaded(val folderId: String): LoadFolderUiEvent()
    data object UnAuthorized: LoadFolderUiEvent()
    data object Error: LoadFolderUiEvent()
}