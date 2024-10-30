package com.pwhs.quickmem.data.dto.study_set

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.data.dto.user.UserResponseDto

data class FolderStudySetResponseDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("flashcardCount")
    val flashcardCount: Int,

    @SerializedName("owner")
    val owner: UserResponseDto

)
