package com.pwhs.quickmem.domain.model.study_set

data class UpdateStudySetResponseModel(
    val id: String,
    val colorId: Long,
    val description: String,
    val isPublic: Boolean,
    val ownerId: String,
    val subjectId: Long,
    val title: String,
    val updatedAt: String,
    val createdAt: String
)