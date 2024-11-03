package com.pwhs.quickmem.presentation.app.library

import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.classes.GetClassDetailResponseModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel

data class LibraryUiState(
    val title: String = "",
    val isLoading: Boolean = false,
    val userAvatar: String = "",
    val username: String = "",
    val token: String = "",
    val userId: String = "",
    val studySets: List<GetStudySetResponseModel> = emptyList(),
    val classes: List<GetClassByOwnerResponseModel> = emptyList(),
    val folders: List<GetFolderResponseModel> = emptyList(),
)
