package com.pwhs.quickmem.presentation.app.classes

sealed class ClassesUiEvent {
    data object OnJoinClass : ClassesUiEvent()
    data object NavigateToWelcome : ClassesUiEvent()
}