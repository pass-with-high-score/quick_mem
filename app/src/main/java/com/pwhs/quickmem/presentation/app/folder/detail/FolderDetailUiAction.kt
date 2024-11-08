package com.pwhs.quickmem.presentation.app.folder.detail

sealed class FolderDetailUiAction {
    data object Refresh : FolderDetailUiAction()
    data object DeleteFolder : FolderDetailUiAction()
    data object EditFolder : FolderDetailUiAction()
}