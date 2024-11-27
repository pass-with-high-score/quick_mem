package com.pwhs.quickmem.data.dto.study_set

import com.google.gson.annotations.SerializedName

data class SaveRecentAccessStudySetRequestDto (
    @SerializedName("userId")
    val userId: String,
    @SerializedName("studySetId")
    val studySetId: String
)