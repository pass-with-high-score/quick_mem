package com.pwhs.quickmem.presentation.app.study_set.add_to_class

import kotlinx.serialization.Serializable

@Serializable
data class AddStudySetToClassesArgs (
    val studySetId : String,
)