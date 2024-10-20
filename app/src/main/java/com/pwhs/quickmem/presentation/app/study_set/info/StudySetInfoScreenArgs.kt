package com.pwhs.quickmem.presentation.app.study_set.info

data class StudySetInfoScreenArgs(
    val title: String,
    val description: String,
    val visibility: String,
    val authorUsername: String,
    val authorAvatarUrl: String,
    val creationDate: String,
)