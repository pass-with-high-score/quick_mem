package com.pwhs.quickmem.presentation.app.study_set.study.quiz

data class LearnByQuizArgs(
    val studySetId: String,
    val studySetTitle: String,
    val studySetDescription: String,
    val studySetColorId: Int,
    val studySetSubjectId: Int,
)