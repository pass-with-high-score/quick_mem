package com.pwhs.quickmem.presentation.app.classes

sealed class ClassesUiAction {
    data object JoinClassClicked : ClassesUiAction()
    data object NavigateToWelcomeClicked : ClassesUiAction()
}