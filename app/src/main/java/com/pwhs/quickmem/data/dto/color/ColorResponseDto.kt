package com.pwhs.quickmem.data.dto.color

import com.google.gson.annotations.SerializedName

data class ColorResponseDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("hexValue")
    val hexValue: String
)