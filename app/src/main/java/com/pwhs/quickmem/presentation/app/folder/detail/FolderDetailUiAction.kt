package com.pwhs.quickmem.presentation.app.folder.detail

import com.pwhs.quickmem.core.data.enums.LearnMode

sealed class FolderDetailUiAction {
    data object Refresh : FolderDetailUiAction()
    data object DeleteFolder : FolderDetailUiAction()
    data object EditFolder : FolderDetailUiAction()
    data class NavigateToLearn(val learnMode: LearnMode, val isGetAll: Boolean) : FolderDetailUiAction()
}