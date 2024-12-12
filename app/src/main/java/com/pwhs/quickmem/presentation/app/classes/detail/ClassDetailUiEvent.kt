package com.pwhs.quickmem.presentation.app.classes.detail

import androidx.annotation.StringRes

sealed class ClassDetailUiEvent {
    data object OnJoinClass : ClassDetailUiEvent()
    data object NavigateToWelcome : ClassDetailUiEvent()
    data object OnNavigateToAddFolder : ClassDetailUiEvent()
    data object OnNavigateToRemoveMembers : ClassDetailUiEvent()
    data object OnNavigateToAddStudySets : ClassDetailUiEvent()
    data class ShowError(@StringRes val message: Int) : ClassDetailUiEvent()
    data object ClassDeleted : ClassDetailUiEvent()
    data object NavigateToEditClass : ClassDetailUiEvent()
    data object ExitClass : ClassDetailUiEvent()
    data object InviteToClassSuccess : ClassDetailUiEvent()
}