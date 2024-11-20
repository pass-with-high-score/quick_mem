package com.pwhs.quickmem.presentation.app.home.components.search_by_subject

sealed class SearchStudySetBySubjectUiAction() {
    data object RefreshStudySets : SearchStudySetBySubjectUiAction()
}