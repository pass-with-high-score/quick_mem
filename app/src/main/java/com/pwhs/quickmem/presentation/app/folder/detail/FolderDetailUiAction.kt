package com.pwhs.quickmem.presentation.app.folder.detail

sealed class FolderDetailUiAction {
    data object Refresh : FolderDetailUiAction()
    data object OnDeleteFolderClicked : FolderDetailUiAction()
    data object OnEditFolderClicked : FolderDetailUiAction()
    data class OnResetProgressClicked(val studySetId: String) : FolderDetailUiAction()

}