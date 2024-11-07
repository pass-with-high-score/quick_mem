package com.pwhs.quickmem.data.dto.classes

data class AddFoldersToClassRequestDto(
    val userId: String,
    val classId: String,
    val folderIds: List<String>
)