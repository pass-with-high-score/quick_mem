package com.pwhs.quickmem.presentation.app.home.search_by_subject

import com.pwhs.quickmem.domain.model.subject.SubjectModel

data class SearchStudySetBySubjectUiState (
    val id: Int = 0,
    val subject: SubjectModel? = null,
    val isLoading: Boolean = false
)