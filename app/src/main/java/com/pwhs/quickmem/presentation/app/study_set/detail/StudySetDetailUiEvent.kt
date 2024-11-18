package com.pwhs.quickmem.presentation.app.study_set.detail

import com.pwhs.quickmem.domain.model.study_set.GetMakeACopyResponseModel

sealed class StudySetDetailUiEvent {
    data object FlashCardDeleted : StudySetDetailUiEvent()
    data object FlashCardStarred : StudySetDetailUiEvent()
    data object NavigateToEditStudySet : StudySetDetailUiEvent()
    data object NavigateToEditFlashCard : StudySetDetailUiEvent()
    data object StudySetDeleted : StudySetDetailUiEvent()
    data object StudySetProgressReset : StudySetDetailUiEvent()
    data class StudySetCopied(val newStudySet: GetMakeACopyResponseModel) : StudySetDetailUiEvent()
}