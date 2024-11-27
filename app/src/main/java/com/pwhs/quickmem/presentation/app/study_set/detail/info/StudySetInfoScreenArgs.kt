package com.pwhs.quickmem.presentation.app.study_set.detail.info

data class StudySetInfoScreenArgs(
    val title: String,
    val description: String,
    val isPublic: Boolean,
    val isAIGenerated: Boolean,
    val authorUsername: String,
    val authorAvatarUrl: String,
    val creationDate: String,
)