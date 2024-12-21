package com.pwhs.quickmem.presentation.app.study_set.add_to_folder

import kotlinx.serialization.Serializable

@Serializable
data class AddStudySetToFoldersArgs (
    val studySetId : String,
)