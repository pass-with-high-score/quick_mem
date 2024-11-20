package com.pwhs.quickmem.presentation.app.home.components.search_by_subject

sealed class SearchStudySetBySubjectUiEvent {
    data class Error(val message: String) : SearchStudySetBySubjectUiEvent()
}