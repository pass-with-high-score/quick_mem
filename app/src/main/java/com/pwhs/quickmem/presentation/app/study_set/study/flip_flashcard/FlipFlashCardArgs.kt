package com.pwhs.quickmem.presentation.app.study_set.study.flip_flashcard

data class FlipFlashCardArgs(
    val studySetId: String,
    val studySetTitle: String,
    val studySetDescription: String,
    val studySetCardCount: Int,
    val studySetColorId: Int,
    val studySetSubjectId: Int,
)
