package com.pwhs.quickmem.domain.model.study_set

import com.pwhs.quickmem.domain.model.users.UserResponseModel

data class FolderStudySetResponseModel(
    val id: String,
    val title: String,
    val flashcardCount: Int,
    val owner: UserResponseModel
)
