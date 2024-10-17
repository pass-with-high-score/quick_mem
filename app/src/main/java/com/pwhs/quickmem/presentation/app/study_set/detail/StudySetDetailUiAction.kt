package com.pwhs.quickmem.presentation.app.study_set.detail

sealed class StudySetDetailUiAction {
    data object Refresh : StudySetDetailUiAction()
}