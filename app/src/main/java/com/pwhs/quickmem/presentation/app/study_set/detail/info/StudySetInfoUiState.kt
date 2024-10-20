package com.pwhs.quickmem.presentation.app.study_set.detail.info

data class StudySetInfoUiState(
    val title: String = "",
    val description: String = "",
    val isPublic: Boolean = false,
    val authorUsername: String = "",
    val authorAvatarUrl: String = "",
    val creationDate: String = "",
)