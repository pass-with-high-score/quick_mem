package com.pwhs.quickmem.data.dto.classes

import com.google.gson.annotations.SerializedName

data class CreateClassResponseDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,

    @SerializedName("joinToken")
    val joinToken: String,

    @SerializedName("allowSetManagement")
    val allowSetManagement: Boolean,

    @SerializedName("allowMemberManagement")
    val allowMemberManagement: Boolean
)
