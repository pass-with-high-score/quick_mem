package com.pwhs.quickmem.data.dto.pixabay

import com.google.gson.annotations.SerializedName

data class PixaBayImageDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageUrl")
    val imageUrl: String,
)
