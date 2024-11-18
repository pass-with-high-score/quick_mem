package com.pwhs.quickmem.domain.model.study_set

data class AddMakeACopyRequestModel(
    val studySetId: String,
    val newOwnerId: String
)