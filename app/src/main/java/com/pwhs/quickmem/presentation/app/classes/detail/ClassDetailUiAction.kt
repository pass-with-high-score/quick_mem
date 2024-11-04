package com.pwhs.quickmem.presentation.app.classes.detail

sealed class ClassDetailUiAction {
    data object JoinClassClicked : ClassDetailUiAction()
    data object NavigateToWelcomeClicked : ClassDetailUiAction()
    data object Refresh : ClassDetailUiAction()
}