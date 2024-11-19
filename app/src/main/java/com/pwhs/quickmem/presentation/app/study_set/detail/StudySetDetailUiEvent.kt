package com.pwhs.quickmem.presentation.app.study_set.detail

sealed class StudySetDetailUiEvent {
    data object FlashCardDeleted : StudySetDetailUiEvent()
    data object FlashCardStarred : StudySetDetailUiEvent()
    data object NavigateToEditStudySet : StudySetDetailUiEvent()
    data object NavigateToEditFlashCard : StudySetDetailUiEvent()
    data object StudySetDeleted : StudySetDetailUiEvent()
    data object StudySetProgressReset : StudySetDetailUiEvent()
    data class StudySetCopied(val newStudySetId: String) : StudySetDetailUiEvent()
}