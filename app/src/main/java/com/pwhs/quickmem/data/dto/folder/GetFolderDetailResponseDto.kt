package com.pwhs.quickmem.data.dto.folder

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.data.dto.study_set.GetStudySetResponseDto
import com.pwhs.quickmem.data.dto.user.UserResponseDto

data class GetFolderDetailResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("isPublic")
    val isPublic: Boolean,
    @SerializedName("studySetCount")
    val studySetCount: Int,
    @SerializedName("user")
    val user: UserResponseDto,
    @SerializedName("studySets")
    val studySets: List<GetStudySetResponseDto>? = null,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
)
