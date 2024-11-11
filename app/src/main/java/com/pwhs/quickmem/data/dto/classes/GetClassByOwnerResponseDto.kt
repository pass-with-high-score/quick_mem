package com.pwhs.quickmem.data.dto.classes

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.data.dto.user.UserResponseDto

data class GetClassByOwnerResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("owner")
    val owner: UserResponseDto,
    @SerializedName("allowMemberManagement")
    val allowMemberManagement: Boolean,
    @SerializedName("allowSetManagement")
    val allowSetManagement: Boolean,
    @SerializedName("folderCount")
    val folderCount: Int,
    @SerializedName("memberCount")
    val memberCount: Int,
    @SerializedName("studySetCount")
    val studySetCount: Int,
    @SerializedName("joinToken")
    val joinToken: String?,
    @SerializedName("isImported")
    val isImported: Boolean?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
