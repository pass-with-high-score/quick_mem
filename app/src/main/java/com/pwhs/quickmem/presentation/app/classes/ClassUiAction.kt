package com.pwhs.quickmem.presentation.app.classes

sealed class ClassUiAction {
    data object JoinClassClicked : ClassUiAction()
    data object NavigateToWelcomeClicked : ClassUiAction()
}