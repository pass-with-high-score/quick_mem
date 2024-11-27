package com.pwhs.quickmem.data.dto.study_time

import com.google.gson.annotations.SerializedName

data class GetStudyTimeByStudySetResponseDto(
    @SerializedName("flip")
    val flip: Int,
    @SerializedName("quiz")
    val quiz: Int,
    @SerializedName("studySetId")
    val studySetId: String,
    @SerializedName("total")
    val total: Int,
    @SerializedName("trueFalse")
    val trueFalse: Int,
    @SerializedName("write")
    val write: Int
)
