package com.pwhs.quickmem.presentation.app.home.search_by_subject

sealed class SearchStudySetBySubjectUiAction() {
    data object RefreshStudySets : SearchStudySetBySubjectUiAction()
}