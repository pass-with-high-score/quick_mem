package com.pwhs.quickmem.presentation.app.deeplink.study_set

data class LoadStudySetUiState(
    val studySetCode: String = "",
    val isLoading: Boolean = true,
    val studySetId: String? = null
)