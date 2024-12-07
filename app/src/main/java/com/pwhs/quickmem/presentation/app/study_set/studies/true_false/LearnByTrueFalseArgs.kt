package com.pwhs.quickmem.presentation.app.study_set.studies.true_false

import com.pwhs.quickmem.core.data.enums.LearnFrom

data class LearnByTrueFalseArgs(
    val studySetId: String,
    val studySetTitle: String,
    val studySetDescription: String,
    val studySetColorId: Int,
    val studySetSubjectId: Int,
    val folderId: String,
    val learnFrom: LearnFrom,
    val isGetAll: Boolean,
)
