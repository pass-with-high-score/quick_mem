package com.pwhs.quickmem.data.dto.study_set

import com.google.gson.annotations.SerializedName

data class AddStudySetToFoldersRequestDto (
    @SerializedName("studySetId")
    val studySetId: String,
    @SerializedName("folderIds")
    val folderIds: List<String>
)