package com.pwhs.quickmem.data.dto.classes

import com.google.gson.annotations.SerializedName

data class DeleteStudySetsRequestDto(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("classId")
    val classId: String,
    @SerializedName("studySetId")
    val studySetId: String
)