package com.pwhs.quickmem.data.dto.study_set

import com.google.gson.annotations.SerializedName

data class StudySetDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("subject")
    val subject: String?,
    @SerializedName("color")
    val color: String,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("isPublic")
    val isPublic: Boolean = false,
    @SerializedName("cards")
    val createdAt: String? = null,
    @SerializedName("cards")
    val updatedAt: String? = null
)
