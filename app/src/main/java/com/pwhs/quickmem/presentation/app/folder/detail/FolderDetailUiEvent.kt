package com.pwhs.quickmem.presentation.app.folder.detail

sealed class FolderDetailUiEvent {
    data object StudySetDeleted : FolderDetailUiEvent()
}