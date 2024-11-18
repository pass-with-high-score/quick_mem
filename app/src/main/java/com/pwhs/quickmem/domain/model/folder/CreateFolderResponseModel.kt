package com.pwhs.quickmem.domain.model.folder

data class CreateFolderResponseModel(
    val id: String,
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val updatedAt: String,
    val createdAt: String,
    val linkShareCode: String? = null,
)
