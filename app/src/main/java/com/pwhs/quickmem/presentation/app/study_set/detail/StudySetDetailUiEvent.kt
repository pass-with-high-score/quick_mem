package com.pwhs.quickmem.presentation.app.study_set.detail

sealed class StudySetDetailUiEvent {
    data object FlashCardDeleted : StudySetDetailUiEvent()
}