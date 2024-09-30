package com.pwhs.quickmem.presentation.app.library

import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel

data class LibraryUiState(
    val title: String = "",
    val studySets: List<GetStudySetResponseModel> = emptyList()
)
