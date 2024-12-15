package com.pwhs.quickmem.data.dto.pixabay

import com.google.gson.annotations.SerializedName

data class SearchImageResponseDto(
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int,
    @SerializedName("images")
    val images: List<PixaBayImageDto>
)
