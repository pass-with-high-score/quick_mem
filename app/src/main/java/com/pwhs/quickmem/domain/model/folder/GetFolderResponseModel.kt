package com.pwhs.quickmem.domain.model.folder

import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel

data class GetFolderResponseModel(
    val id: String,
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val studySetCount: Int,
    val ownerId: String,
    val user: UserResponseModel,
    val studySets: List<GetStudySetResponseModel>,
    val createdAt: String,
    val updatedAt: String,
)