package com.pwhs.quickmem.domain.model.folder

import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel

data class GetFolderResponseModel(
    val id: String,
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val studySetCount: Int,
    val user: UserResponseModel,
    val studySets: List<GetStudySetResponseModel>? = null,
    val createdAt: String,
    val updatedAt: String,
)