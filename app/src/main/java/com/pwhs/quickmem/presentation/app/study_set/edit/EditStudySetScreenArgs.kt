package com.pwhs.quickmem.presentation.app.study_set.edit

import kotlinx.serialization.Serializable

@Serializable
data class EditStudySetScreenArgs(
    val studySetId: String,
    val studySetTitle: String,
    val studySetDescription: String,
    val studySetSubjectId: Int,
    val studySetColorId: Int,
    val studySetIsPublic: Boolean
)
