package com.pwhs.quickmem.data.dto.folder

data class UpdateFolderRequestDto (
    val title: String,
    val description: String,
    val isPublic: Boolean
)