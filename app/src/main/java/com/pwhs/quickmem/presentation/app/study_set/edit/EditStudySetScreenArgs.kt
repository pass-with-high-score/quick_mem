package com.pwhs.quickmem.presentation.app.study_set.edit


data class EditStudySetScreenArgs(
    val studySetId: String,
    val studySetTitle: String,
    val studySetDescription: String,
    val studySetSubjectId: Int,
    val studySetColorId: Int,
    val studySetIsPublic: Boolean
)
