package com.pwhs.quickmem.presentation.app.study_set.detail

sealed class StudySetDetailUiAction {
    data object Refresh : StudySetDetailUiAction()
    data class OnIdOfFlashCardSelectedChanged(val id: String) : StudySetDetailUiAction()
    data object OnDeleteFlashCardClicked : StudySetDetailUiAction()
    data class OnStarFlashCardClicked(val id: String, val isStarred: Boolean) :
        StudySetDetailUiAction()
}