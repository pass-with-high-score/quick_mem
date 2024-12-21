package com.pwhs.quickmem.presentation.app.study_set.studies.flip

import com.pwhs.quickmem.core.data.enums.LearnFrom
import kotlinx.serialization.Serializable

@Serializable
data class FlipFlashCardArgs(
    val studySetId: String,
    val studySetTitle: String,
    val studySetDescription: String,
    val studySetColorId: Int,
    val studySetSubjectId: Int,
    val folderId: String,
    val learnFrom: LearnFrom,
    val isGetAll: Boolean,
)
