package com.pwhs.quickmem.presentation.app.classes.detail

sealed class ClassDetailUiAction {
    data object JoinClassClicked : ClassDetailUiAction()
    data object NavigateToWelcomeClicked : ClassDetailUiAction()
    data object Refresh : ClassDetailUiAction()
    data object EditClass : ClassDetailUiAction()
    data object DeleteClass : ClassDetailUiAction()
}