package com.pwhs.quickmem.presentation.app.classes.detail

sealed class ClassDetailUiAction {
    data object NavigateToWelcomeClicked : ClassDetailUiAction()
    data object Refresh : ClassDetailUiAction()
    data object EditClass : ClassDetailUiAction()
    data object DeleteClass : ClassDetailUiAction()
    data object OnNavigateToAddFolder:ClassDetailUiAction()
    data object OnNavigateToAddStudySets:ClassDetailUiAction()
    data object OnNavigateToAddMember:ClassDetailUiAction()
}