package com.pwhs.quickmem.data.dto.classes

import com.google.gson.annotations.SerializedName

data class AddStudySetToClassesRequestDto (
    @SerializedName("studySetId")
    val studySetId: String,
    @SerializedName("classIds")
    val classIds: List<String>
)