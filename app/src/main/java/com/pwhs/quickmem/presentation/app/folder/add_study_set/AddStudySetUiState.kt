package com.pwhs.quickmem.presentation.app.folder.add_study_set

import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel

data class AddStudySetUiState (
    val isLoading : Boolean = false,
    val userAvatar: String = "",
    val username: String = "",
    val token: String = "",
    val userId: String = "",
    val studySets: List<GetStudySetResponseModel> = emptyList(),
)