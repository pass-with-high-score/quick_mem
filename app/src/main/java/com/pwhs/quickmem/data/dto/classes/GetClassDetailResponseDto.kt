package com.pwhs.quickmem.data.dto.classes

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.data.dto.folder.GetFolderResponseDto
import com.pwhs.quickmem.data.dto.study_set.GetStudySetResponseDto
import com.pwhs.quickmem.data.dto.user.ClassMemberDto
import com.pwhs.quickmem.data.dto.user.UserResponseDto

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
    @SerializedName("allowMemberManagement")
    val allowMemberManagement: Boolean,
    @SerializedName("allowSetManagement")
    val allowSetManagement: Boolean,
    @SerializedName("folderCount")
    val folderCount: Int,
    @SerializedName("folders")
    val folders: List<GetFolderResponseDto>? = emptyList(),
    @SerializedName("memberCount")
    val memberCount: Int,
    @SerializedName("members")
    val members: List<ClassMemberDto>? = emptyList(),
    @SerializedName("studySetCount")
    val studySetCount: Int,
    @SerializedName("studySets")
    val studySets: List<GetStudySetResponseDto>? = emptyList(),
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("isJoined")
    val isJoined: Boolean? = false
)
