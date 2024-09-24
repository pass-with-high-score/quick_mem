package com.pwhs.quickmem.domain.model.study_set

data class StudySetModel(
    val id: String,
    val name: String,
    val description: String?,
    val subject: String?,
    val color: String,
    val owner: String,
    val isPublic: Boolean = false,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
