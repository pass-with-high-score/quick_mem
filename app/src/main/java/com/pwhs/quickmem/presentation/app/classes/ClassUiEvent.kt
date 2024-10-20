package com.pwhs.quickmem.presentation.app.classes

sealed class ClassUiEvent {
    data object OnJoinClass : ClassUiEvent()
    data object NavigateToWelcome : ClassUiEvent()
}