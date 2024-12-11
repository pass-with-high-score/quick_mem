package com.pwhs.quickmem.data.dto.subject

import com.google.gson.annotations.SerializedName

data class SubjectResponseDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = null,
)