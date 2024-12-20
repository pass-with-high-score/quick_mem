package com.pwhs.quickmem.presentation.app.deeplink.study_set

import kotlinx.serialization.Serializable

@Serializable
data class LoadStudySetArgs(
    val studySetCode: String,
)