package com.pwhs.quickmem.data.dto.study_set

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.data.dto.color.ColorResponseDto
import com.pwhs.quickmem.data.dto.subject.SubjectResponseDto

data class GetStudySetResponseDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("isPublic")
    val isPublic: Boolean,

    @SerializedName("createdAt")
    val createdAt: String, // Sử dụng String hoặc LocalDateTime

    @SerializedName("updatedAt")
    val updatedAt: String,

    @SerializedName("ownerId")
    val ownerId: String,

    @SerializedName("subject")
    val subject: SubjectResponseDto? = null,

    @SerializedName("color")
    val color: ColorResponseDto? = null
)
