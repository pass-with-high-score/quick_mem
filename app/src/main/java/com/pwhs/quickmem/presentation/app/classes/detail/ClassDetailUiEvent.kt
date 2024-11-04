package com.pwhs.quickmem.presentation.app.classes.detail

sealed class ClassDetailUiEvent {
    data object OnJoinClass : ClassDetailUiEvent()
    data object NavigateToWelcome : ClassDetailUiEvent()
    data class ShowError(val message: String) : ClassDetailUiEvent()
    data object ClassDeleted : ClassDetailUiEvent()
    data object NavigateToEditClass : ClassDetailUiEvent()
}