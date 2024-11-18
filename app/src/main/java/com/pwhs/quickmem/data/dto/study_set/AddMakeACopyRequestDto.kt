package com.pwhs.quickmem.data.dto.study_set

import com.google.gson.annotations.SerializedName

data class AddMakeACopyRequestDto(
    @SerializedName("studySetId")
    val studySetId: String,
    @SerializedName("newOwnerId")
    val newOwnerId: String
)

