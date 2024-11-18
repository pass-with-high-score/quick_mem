package com.pwhs.quickmem.presentation.app.deeplink.study_set

sealed class LoadStudySetUiEvent {
    data class StudySetLoaded(val studySetId: String): LoadStudySetUiEvent()
    data object UnAuthorized: LoadStudySetUiEvent()
    data object NotFound: LoadStudySetUiEvent()
}