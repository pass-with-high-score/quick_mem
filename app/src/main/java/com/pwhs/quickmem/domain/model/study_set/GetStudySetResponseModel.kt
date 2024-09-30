package com.pwhs.quickmem.domain.model.study_set

import com.pwhs.quickmem.data.dto.color.ColorResponseDto
import com.pwhs.quickmem.data.dto.subject.SubjectResponseDto

data class GetStudySetResponseModel(
    val id: String,
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val ownerId: String,
    val subject: SubjectResponseDto? = null,
    val color: ColorResponseDto? = null
)
