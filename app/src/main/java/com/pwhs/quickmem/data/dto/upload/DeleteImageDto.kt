package com.pwhs.quickmem.data.dto.upload

import com.google.gson.annotations.SerializedName

data class DeleteImageDto(
    @SerializedName("imageURL")
    val imageURL: String? = null
)