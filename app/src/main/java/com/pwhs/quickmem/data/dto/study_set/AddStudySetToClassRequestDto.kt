package com.pwhs.quickmem.data.dto.study_set

import com.google.gson.annotations.SerializedName

data class AddStudySetToClassRequestDto (
    @SerializedName("userId")
    val userId: String,
    @SerializedName("classId")
    val classId: String,
    @SerializedName("studySetIds")
    val studySetIds: List<String>
)