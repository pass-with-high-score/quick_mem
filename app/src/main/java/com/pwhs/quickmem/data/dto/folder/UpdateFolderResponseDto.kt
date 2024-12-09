package com.pwhs.quickmem.data.dto.folder

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.data.dto.study_set.GetStudySetResponseDto

data class UpdateFolderResponseDto(
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
    @SerializedName("studySets")
    val studySets: List<GetStudySetResponseDto>,
    @SerializedName("linkShareCode")
    val linkShareCode: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("createdAt")
    val createdAt: String
)