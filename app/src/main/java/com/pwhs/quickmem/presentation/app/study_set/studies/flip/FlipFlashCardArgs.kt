package com.pwhs.quickmem.presentation.app.study_set.studies.flip

data class FlipFlashCardArgs(
    val studySetId: String,
    val studySetTitle: String,
    val studySetDescription: String,
    val studySetColorId: Int,
    val studySetSubjectId: Int,
)
