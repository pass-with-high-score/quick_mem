package com.pwhs.quickmem.presentation.app.classes.detail

sealed class ClassDetailUiAction {
    data object NavigateToWelcomeClicked : ClassDetailUiAction()
    data object NavigateToRemoveMembers : ClassDetailUiAction()
    data object Refresh : ClassDetailUiAction()
    data object EditClass : ClassDetailUiAction()
    data object DeleteClass : ClassDetailUiAction()
    data class OnDeleteStudySetInClass(val studySetId: String) : ClassDetailUiAction()
    data class OnDeleteMember(val memberId: String) : ClassDetailUiAction()
    data object OnNavigateToAddFolder : ClassDetailUiAction()
    data object OnNavigateToAddStudySets : ClassDetailUiAction()
    data object OnJoinClass : ClassDetailUiAction()
    data object ExitClass : ClassDetailUiAction()
}