package com.pwhs.quickmem.presentation.app.classes.add_folder

sealed class AddFolderToClassUIAction {
    data object Refresh : AddFolderToClassUIAction()
    data object RefreshFolders : AddFolderToClassUIAction()
    data object SaveClicked : AddFolderToClassUIAction()
}