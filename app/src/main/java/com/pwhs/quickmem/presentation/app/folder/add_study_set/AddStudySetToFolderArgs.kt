package com.pwhs.quickmem.presentation.app.folder.add_study_set

import kotlinx.serialization.Serializable

@Serializable
data class AddStudySetToFolderArgs (
    val folderId: String,
)