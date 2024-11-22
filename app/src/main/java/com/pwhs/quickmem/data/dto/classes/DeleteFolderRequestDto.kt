package com.pwhs.quickmem.data.dto.classes

import com.google.gson.annotations.SerializedName

data class DeleteFolderRequestDto(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("classId")
    val classId: String,
    @SerializedName("folderId")
    val folderId: String
)