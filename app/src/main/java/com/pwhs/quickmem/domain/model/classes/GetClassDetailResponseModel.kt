package com.pwhs.quickmem.domain.model.classes

import com.pwhs.quickmem.data.dto.user.UserResponseDto
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel

data class GetClassDetailResponseModel(
    val id: String,
    val title: String,
    val description: String,
    val owner: UserResponseDto,
    val joinToken: String,
    val createdAt: String,
    val updatedAt: String,
    val allowMemberManagement: Boolean,
    val allowSetManagement: Boolean,
    val folderCount: Int,
    val folders: List<GetFolderResponseModel>,
    val memberCount: Int,
    val members: List<Unit>,
    val studySetCount: Int,
    val studySets: List<GetStudySetResponseModel>
)
