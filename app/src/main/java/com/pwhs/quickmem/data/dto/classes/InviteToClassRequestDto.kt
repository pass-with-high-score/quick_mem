package com.pwhs.quickmem.data.dto.classes

import com.google.gson.annotations.SerializedName

data class InviteToClassRequestDto(
    @SerializedName("classId")
    val classId: String,
    @SerializedName("username")
    val username: String,
)