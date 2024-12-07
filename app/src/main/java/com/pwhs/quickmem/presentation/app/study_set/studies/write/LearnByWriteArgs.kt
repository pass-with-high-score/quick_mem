package com.pwhs.quickmem.presentation.app.study_set.studies.write

import com.pwhs.quickmem.core.data.enums.LearnFrom

data class LearnByWriteArgs(
    val studySetId: String,
    val studySetTitle: String,
    val studySetDescription: String,
    val studySetColorId: Int,
    val studySetSubjectId: Int,
    val isGetAll: Boolean,
    val folderId: String,
    val learnFrom: LearnFrom,
)
