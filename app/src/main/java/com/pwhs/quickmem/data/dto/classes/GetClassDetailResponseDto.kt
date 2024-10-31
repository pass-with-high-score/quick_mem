package com.pwhs.quickmem.data.dto.classes

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.data.dto.user.UserResponseDto
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel

data class GetClassDetailResponseDto(
    @SerializedName("id")
    val id: String,


    @SerializedName("title")
    val title: String,


    @SerializedName("description")
    val description: String,


    @SerializedName("owner")
    val owner: UserResponseDto,


    @SerializedName("joinToken")
    val joinToken: String,


    @SerializedName("createdAt")
    val createdAt: String,


    @SerializedName("updatedAt")
    val updatedAt: String,


    @SerializedName("allowMemberManagement")
    val allowMemberManagement: Boolean,


    @SerializedName("allowSetManagement")
    val allowSetManagement: Boolean,


    @SerializedName("folderCount")
    val folderCount: Int,


    @SerializedName("folders")
    val folders: List<GetFolderResponseModel>,


    @SerializedName("memberCount")
    val memberCount: Int,


    @SerializedName("members")
    val members: List<Unit>,


    @SerializedName("studySetCount")
    val studySetCount: Int,


    @SerializedName("studySets")
    val studySets: List<GetStudySetResponseModel>
)
