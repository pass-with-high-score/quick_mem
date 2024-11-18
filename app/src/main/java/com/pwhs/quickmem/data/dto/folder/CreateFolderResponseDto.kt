package com.pwhs.quickmem.data.dto.folder

import com.google.gson.annotations.SerializedName

data class CreateFolderResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("isPublic")
    val isPublic: Boolean,
    @SerializedName("linkShareCode")
    val linkShareCode: String? = null,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
