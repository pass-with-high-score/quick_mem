package com.pwhs.quickmem.data.dto.user

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.data.dto.classes.GetClassByOwnerResponseDto
import com.pwhs.quickmem.data.dto.folder.GetFolderDetailResponseDto
import com.pwhs.quickmem.data.dto.study_set.GetStudySetResponseDto

data class UserDetailResponseDto(
    @SerializedName("avatarUrl")
    val avatarUrl: String,
    @SerializedName("classes")
    val classes: List<GetClassByOwnerResponseDto>,
    @SerializedName("folders")
    val folders: List<GetFolderDetailResponseDto>,
    @SerializedName("fullname")
    val fullName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("studySets")
    val studySets: List<GetStudySetResponseDto>,
    @SerializedName("username")
    val username: String
)