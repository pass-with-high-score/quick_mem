package com.pwhs.quickmem.presentation.app.study_set.add_to_class

import com.pwhs.quickmem.domain.model.classes.GetClassDetailResponseModel

data class AddStudySetToClassesUiState(
    val studySetId : String = "",
    val isImported: Boolean = false,
    val isLoading : Boolean = false,
    val userAvatar: String = "",
    val username: String = "",
    val token: String = "",
    val userId: String = "",
    val classImportedIds: List<String> = emptyList(),
    val classes: List<GetClassDetailResponseModel> = emptyList(),
)