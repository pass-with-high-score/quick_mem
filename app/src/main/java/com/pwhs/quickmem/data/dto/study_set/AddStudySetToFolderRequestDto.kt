package com.pwhs.quickmem.data.dto.study_set

import com.google.gson.annotations.SerializedName

data class AddStudySetToFolderRequestDto (
    @SerializedName("folderId")
    val folderId: String,
    @SerializedName("studySetIds")
    val studySetIds: List<String>
)