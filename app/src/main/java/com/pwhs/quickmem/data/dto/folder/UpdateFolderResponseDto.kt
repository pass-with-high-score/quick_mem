package com.pwhs.quickmem.data.dto.folder

import com.pwhs.quickmem.data.dto.study_set.GetStudySetResponseDto

data class UpdateFolderResponseDto (
    val id: String,
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val studySetCount: Int,
    val studySets: List<GetStudySetResponseDto>,
    val updatedAt: String,
    val createdAt: String
)